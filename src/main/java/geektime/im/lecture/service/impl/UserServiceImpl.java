package geektime.im.lecture.service.impl;

import geektime.im.lecture.dao.ImMsgContactMapper;
import geektime.im.lecture.dao.ImMsgContentMapper;
import geektime.im.lecture.dao.ImMsgRelationMapper;
import geektime.im.lecture.dao.ImUserMapper;
import geektime.im.lecture.entity.ImMsgContact;
import geektime.im.lecture.entity.ImMsgContent;
import geektime.im.lecture.entity.ImUser;
import geektime.im.lecture.exceptions.BaseException;
import geektime.im.lecture.response.CommonEnum;
import geektime.im.lecture.service.UserService;
import geektime.im.lecture.vo.MessageContactVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ImUserMapper userRepository;

    @Autowired
    private ImMsgContactMapper contactRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ImMsgContentMapper contentRepository;

    @Override
    public ImUser login(String email, String password) {
        List<ImUser> users = userRepository.findByEmail(email);
        if (null == users || users.isEmpty()) {
            log.warn("该用户不存在:" + email);
            throw new BaseException(CommonEnum.ACCOUNT_WRONG);
        }
        ImUser user = users.get(0);
        if (user.getPassword().equals(password)) {
            log.info(user.getUsername() + " logged in!");
        } else {
            log.warn(user.getUsername() + " failed to log in!");
            throw new BaseException(CommonEnum.ACCOUNT_WRONG);
        }
        return user;
    }


    @Override
    public List<ImUser> getAllUsersExcept(Integer exceptUid) {
        List<ImUser> otherUsers = userRepository.findUsersByUidIsNot(exceptUid);
        return otherUsers;
    }

    @Override
    public List<ImUser> getAllUsersExcept(ImUser exceptUser) {
        List<ImUser> otherUsers = userRepository.findUsersByUidIsNot(exceptUser.getUid());
        return otherUsers;
    }

    @Override
    public MessageContactVO getContacts(ImUser ownerUser) {
        List<ImMsgContact> contacts = contactRepository.findMessageContactsByOwnerUid(ownerUser.getUid());
        if (contacts != null) {
            Integer totalUnread = 0;
            Object totalUnreadObj = redisTemplate.opsForValue().get(ownerUser.getUid() + "_T");
            if (null != totalUnreadObj) {
                totalUnread = Integer.parseInt((String) totalUnreadObj);
            }
            MessageContactVO contactVO = new MessageContactVO(ownerUser.getUid(), ownerUser.getUsername(), ownerUser.getAvatar(), totalUnread);
            contacts.forEach(contact -> {
                System.out.println(1231);
                System.out.println(contact.getOtherUid());
                Integer mid = contact.getMid();
                ImMsgContent contentVO = contentRepository.findByMid(mid);
                ImUser otherUser = userRepository.findByUid(contact.getOtherUid());
                if (null != contentVO) {
                    Integer convUnread = 0;
                    Object convUnreadObj = redisTemplate.opsForHash().get(ownerUser.getUid() + "_C", otherUser.getUid());
                    if (null != convUnreadObj) {
                        convUnread = Integer.parseInt((String) convUnreadObj);
                    }
                    MessageContactVO.ContactInfo contactInfo = contactVO.new ContactInfo(otherUser.getUid(), otherUser.getUsername(), otherUser.getAvatar(), mid, contact.getType(), contentVO.getContent(), convUnread, contact.getCreateTime());
                    contactVO.appendContact(contactInfo);
                }
            });
            return contactVO;
        }
        return null;
    }
}
