package geektime.im.lecture.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import geektime.im.lecture.Constants;
import geektime.im.lecture.dao.*;
import geektime.im.lecture.entity.*;
import geektime.im.lecture.service.GroupService;
import geektime.im.lecture.service.MessageService;
import geektime.im.lecture.service.UserService;
import geektime.im.lecture.vo.GroupMsgVo;
import geektime.im.lecture.vo.LoginResVo;
import geektime.im.lecture.vo.MessageContactVO;
import geektime.im.lecture.vo.MessageVO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {

    @Autowired
    private ImMsgContentMapper contentRepository;
    @Autowired
    private ImMsgRelationMapper relationRepository;
    @Autowired
    private ImMsgContactMapper contactRepository;
    @Autowired
    private ImUserMapper userRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ImGroupMsgMapper groupMsgMapper;

    @Override
    @Transactional
    public MessageVO sendNewMsg(String senderUid, String recipientUid, String content, Integer msgType) {
        Date currentTime = new Date();
        /**存内容*/
        ImMsgContent messageContent = new ImMsgContent();
        messageContent.setSenderId(senderUid);
        messageContent.setRecipientId(recipientUid);
        messageContent.setContent(content);
        messageContent.setMsgType(msgType);
        messageContent.setCreateTime(currentTime);
        contentRepository.insertGetMid(messageContent);
        Integer mid = messageContent.getMid();
        /**存发件人的发件箱*/
        ImMsgRelation messageRelationSender = new ImMsgRelation();
        messageRelationSender.setMid(mid);
        messageRelationSender.setOwnerUid(senderUid);
        messageRelationSender.setOtherUid(recipientUid);
        messageRelationSender.setType(0);
        messageRelationSender.setCreateTime(currentTime);
        relationRepository.insert(messageRelationSender);

        /**存收件人的收件箱*/
        ImMsgRelation messageRelationRecipient = new ImMsgRelation();
        messageRelationRecipient.setMid(mid);
        messageRelationRecipient.setOwnerUid(recipientUid);
        messageRelationRecipient.setOtherUid(senderUid);
        messageRelationRecipient.setType(1);
        messageRelationRecipient.setCreateTime(currentTime);
        relationRepository.insert(messageRelationRecipient);

        /**更新发件人的最近联系人 */
        ImMsgContact messageContactSender = contactRepository.findMsgByOwnerIdAndOtherId(senderUid, recipientUid);
        if (messageContactSender != null) {
            messageContactSender.setMid(mid);
            contactRepository.updateContact(messageContactSender);
        } else {
            messageContactSender = new ImMsgContact();
            messageContactSender.setOwnerUid(senderUid);
            messageContactSender.setOtherUid(recipientUid);
            messageContactSender.setMid(mid);
            messageContactSender.setCreateTime(currentTime);
            messageContactSender.setType(0);
            contactRepository.insert(messageContactSender);
        }


        /**更新收件人的最近联系人 */
        ImMsgContact messageContactRecipient = contactRepository.findMsgByOwnerIdAndOtherId(recipientUid, senderUid);
        if (messageContactRecipient != null) {
            messageContactRecipient.setMid(mid);
            contactRepository.updateContact(messageContactRecipient);
        } else {
            messageContactRecipient = new ImMsgContact();
            messageContactRecipient.setOwnerUid(recipientUid);
            messageContactRecipient.setOtherUid(senderUid);
            messageContactRecipient.setMid(mid);
            messageContactRecipient.setCreateTime(currentTime);
            messageContactRecipient.setType(1);
            contactRepository.insert(messageContactRecipient);
        }


        /**更未读更新 */
        //加总未读
        redisTemplate.opsForValue().increment(recipientUid + "_T", 1);
        //加会话未读
        redisTemplate.opsForHash().increment(recipientUid + "_C", senderUid, 1);

        /** 待推送消息发布到redis */
        ImUser self = userRepository.findByUid(senderUid);
        ImUser other = userRepository.findByUid(recipientUid);
        MessageVO messageVO = new MessageVO(mid, content, self.getUid(), messageContactSender.getType(), other.getUid(), messageContent.getCreateTime(), self.getAvatar(), other.getAvatar(), self.getUsername(), other.getUsername());
        redisTemplate.convertAndSend(Constants.WEBSOCKET_MSG_TOPIC, JSONObject.toJSONString(messageVO));
        return messageVO;
    }

    @Override
    public List<MessageVO> queryConversationMsg(String ownerUid, String otherUid) {
        List<ImMsgRelation> relationList = relationRepository.findAllByOwnerUidAndOtherUidOrderByMidAsc(ownerUid, otherUid);
        return composeMessageVO(relationList, ownerUid, otherUid);
    }

    @Override
    public List<MessageVO> queryNewerMsgFrom(String ownerUid, String otherUid, Integer fromMid) {
        List<ImMsgRelation> relationList = relationRepository.findAllByOwnerUidAndOtherUidSinceMid(ownerUid, otherUid, fromMid);
        return composeMessageVO(relationList, ownerUid, otherUid);
    }

    private List<MessageVO> composeMessageVO(List<ImMsgRelation> relationList, String ownerUid, String otherUid) {
        if (null != relationList && !relationList.isEmpty()) {
            /** 先拼接消息索引和内容 */
            List<MessageVO> msgList = Lists.newArrayList();
            ImUser self = userRepository.findByUid(ownerUid);
            ImUser other = userRepository.findByUid(otherUid);
            relationList.stream().forEach(relation -> {
                Integer mid = relation.getMid();
                ImMsgContent contentVO = contentRepository.findByMid(mid);
                if (null != contentVO) {
                    String content = contentVO.getContent();
                    MessageVO messageVO = new MessageVO(mid, content, relation.getOwnerUid(), relation.getType(), relation.getOtherUid(), relation.getCreateTime(), self.getAvatar(), other.getAvatar(), self.getUsername(), other.getUsername());
                    msgList.add(messageVO);
                }
            });

            /** 再变更未读 */
            Object convUnreadObj = redisTemplate.opsForHash().get(ownerUid + Constants.CONVERSION_UNREAD_SUFFIX, otherUid);
            if (null != convUnreadObj) {
                long convUnread = Long.parseLong((String) convUnreadObj);
                redisTemplate.opsForHash().delete(ownerUid + Constants.CONVERSION_UNREAD_SUFFIX, otherUid);
                long afterCleanUnread = redisTemplate.opsForValue().increment(ownerUid + Constants.TOTAL_UNREAD_SUFFIX, -convUnread);
                /** 修正总未读 */
                if (afterCleanUnread <= 0) {
                    redisTemplate.delete(ownerUid + Constants.TOTAL_UNREAD_SUFFIX);
                }
            }
            return msgList;
        }
        return null;
    }

    @Override
    public MessageContactVO queryContacts(ImUser ownerUser) {
        List<ImMsgContact> contacts = contactRepository.findMessageContactsByOwnerUid(ownerUser.getUid());
        if (contacts != null) {
            Integer totalUnread = 0;
            MessageContactVO contactVO = new MessageContactVO(ownerUser.getUid(), ownerUser.getUsername(), ownerUser.getAvatar(), totalUnread);
            contacts.forEach(contact -> {
                Integer mid = contact.getMid();
                if (contact.getType() > 1) {
                    ImMsgContent contentVO = contentRepository.findByMid(mid);
                    ImUser otherUser = userRepository.findByUid(contact.getOtherUid());
                    if (null != contentVO) {
                        Integer convUnread = 0;
                        Object convUnreadObj = redisTemplate.opsForHash().get(ownerUser.getUid() + Constants.CONVERSION_UNREAD_SUFFIX, otherUser.getUid());
                        if (null != convUnreadObj) {
                            convUnread = Integer.parseInt((String) convUnreadObj);
                        }
                        MessageContactVO.ContactInfo contactInfo = contactVO.new ContactInfo(otherUser.getUid(), otherUser.getUsername(), otherUser.getAvatar(), mid, contact.getType(), contentVO.getContent(), convUnread, contact.getCreateTime());
                        contactVO.appendContact(contactInfo);
                    }
                } else {
                    //最近群聊
                    ImGroupMsg groupMsg = groupMsgMapper.queryMsgByMid(mid);
                    ImGroupInfo groupInfo = groupService.queryGroupByGroupId(groupMsg.getGroupId());
                    if (null != groupMsg) {
                        Integer convUnread = 0;
                        Object convUnreadObj = redisTemplate.opsForHash().get(ownerUser.getUid() + Constants.GROUP_MESSAGE_SUFFIX, groupMsg.getGroupId());
                        if (null != convUnreadObj) {
                            convUnread = Integer.parseInt((String) convUnreadObj);
                        }
                        MessageContactVO.ContactInfo contactInfo = contactVO.new ContactInfo(groupMsg.getGroupId(), groupInfo.getGroupName(), groupInfo.getGroupAvatar(), mid, contact.getType(), groupMsg.getContent(), convUnread, contact.getCreateTime());
                        contactVO.appendContact(contactInfo);
                    }
                }

            });
            return contactVO;
        }
        return null;
    }

    @Override
    public Integer queryTotalUnread(String ownerUid) {
        Integer totalUnread = 0;
        Object totalUnreadObj = redisTemplate.opsForValue().get(ownerUid + Constants.TOTAL_UNREAD_SUFFIX);
        if (null != totalUnreadObj) {
            totalUnread = Integer.parseInt((String) totalUnreadObj);
        }
        return totalUnread;
    }

    @Override
    public LoginResVo queryLoginData(String uid) {
        LoginResVo loginResVo = new LoginResVo();
        ImUser loginUser = userService.getUserByUid(uid);
        loginResVo.setLoginUser(loginUser);
        List<ImUser> otherUsers = userService.getAllUsersExcept(loginUser);
        loginResVo.setOtherUsers(otherUsers);
        MessageContactVO contactVO = queryContacts(loginUser);
        loginResVo.setContactVO(contactVO);
        return loginResVo;
    }

    @Override
    public List<GroupMsgVo> queryGroupMsg(String groupId) {
        return groupService.queryMsgById(groupId);
    }

    @Override
    public List<GroupMsgVo> queryGroupMsgByMid(String groupId, Integer mid) {
        return groupService.queryGroupMsgByMid(groupId, mid);
    }

    @Override
    public List<ImUser> queryUsersByGroupId(String groupId) {
        //先取redis
        List<ImUser> imUserList;
        Object list = redisTemplate.opsForValue().get(groupId + Constants.GROUP_MESSAGE_SUFFIX);
        if (null != list) {
            imUserList = JSONObject.parseArray(list.toString(), ImUser.class);
            ;
        } else {
            imUserList = groupService.queryUsersByGroupId(groupId);
            String json = JSONObject.toJSONString(imUserList);
            //存redis，设置期限为1天
            redisTemplate.opsForValue().set(groupId.toString() + Constants.GROUP_MESSAGE_SUFFIX, json, Duration.ofDays(1));
        }
        return imUserList;
    }

    @Override
    @Transactional
    public GroupMsgVo sendGroupMessage(String sId, String gId, String groupContent) {
        Date currentTime = new Date();
        GroupMsgVo groupMsgVo = new GroupMsgVo();
        groupMsgVo.setGroupId(gId);
        groupMsgVo.setContent(groupContent);
        groupMsgVo.setCreateTime(currentTime);
        groupMsgVo.setOwnerUid(sId);
        ImUser user = userRepository.findByUid(sId);
        groupMsgVo.setAvatar(user.getAvatar());
        groupMsgVo.setOwnerName(user.getUsername());
        ImGroupMsg imGroupMsg = new ImGroupMsg();
        imGroupMsg.setGroupId(gId);
        imGroupMsg.setSendId(sId);
        imGroupMsg.setContent(groupContent);
        imGroupMsg.setSendAvatar("zhangsan.png");
        imGroupMsg.setSendTime(currentTime);
        imGroupMsg.setSendName(user.getUsername());
        //将消息存入群聊消息记录表
        groupMsgMapper.insertGetMid(imGroupMsg);
        //更新最近联系人表
        Integer mid = imGroupMsg.getMid();
        ImMsgContact imMsgContact = contactRepository.findMsgByOwnerIdAndOtherId(sId, gId);
        if (null == imMsgContact) {
            //若为空，则插入一条记录
            imMsgContact = new ImMsgContact();
            imMsgContact.setMid(mid);
            imMsgContact.setOwnerUid(sId);
            imMsgContact.setOtherUid(gId);
            imMsgContact.setCreateTime(currentTime);
            imMsgContact.setType(2);
            contactRepository.insert(imMsgContact);
        } else {
            //否则更新该记录
            imMsgContact.setMid(mid);
            contactRepository.updateContact(imMsgContact);
        }
        redisTemplate.convertAndSend(Constants.WEBSOCKET_GROUP_MSG_TOPIC, JSONObject.toJSONString(groupMsgVo));
        return groupMsgVo;
    }
}
