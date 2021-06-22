package geektime.im.lecture.service;


import geektime.im.lecture.entity.ImUser;
import geektime.im.lecture.vo.GroupMsgVo;
import geektime.im.lecture.vo.LoginResVo;
import geektime.im.lecture.vo.MessageContactVO;
import geektime.im.lecture.vo.MessageVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    /**
     * 发新消息
     *
     * @param senderUid
     * @param recipientUid
     * @param content
     * @param msgType
     * @return
     */
    MessageVO sendNewMsg(String senderUid, String recipientUid, String content, Integer msgType);

    /**
     * 查两人的历史消息
     * @param ownerUid
     * @param otherUid
     * @return
     */
    List<MessageVO> queryConversationMsg(String ownerUid, String otherUid);

    /**
     * 查询两人从某一条消息开始的新消息
     * @param ownerUid
     * @param otherUid
     * @param fromMid
     * @return
     */
    List<MessageVO> queryNewerMsgFrom(String ownerUid, String otherUid, Integer fromMid);

    /**
     * 查询某个用户的最近联系人
     * @param ownerUid
     * @return
     */
    MessageContactVO queryContacts(ImUser ownerUser);

    /**
     * 查询某人总未读
     * @param ownerUid
     * @return
     */
    Integer queryTotalUnread(String ownerUid);

    /**
     * 根据邮箱查找用户登陆之后的首页信息
     * @param uid
     * @return
     */
    LoginResVo queryLoginData(String uid);

    /**
     * 查询该群最新的50条消息
     * @param groupId
     * @return
     */
    List<GroupMsgVo> queryGroupMsg(String groupId);

    /**
     * 查询大于mid的所有消息
     * @param groupId
     * @param mid
     * @return
     */
    List<GroupMsgVo> queryGroupMsgByMid(String groupId, Integer mid);

    /**
     * 查询群组里的所有用户
     * @param groupId
     * @return
     */
    List<String> queryUsersByGroupId(String groupId);

    /**
     * 发送群聊消息
     * @param sId
     * @param gId
     * @param groupContent
     * @return
     */
    GroupMsgVo sendGroupMessage(String sId, String gId, String groupContent);

    /**
     * 删除发送的好友请求消息
     * @param sendUid
     * @param recipientUid
     */
    void deleteFriendRequest(String sendUid, String recipientUid);
}
