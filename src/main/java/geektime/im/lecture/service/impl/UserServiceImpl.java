package geektime.im.lecture.service.impl;

import geektime.im.lecture.Constants;
import geektime.im.lecture.dao.*;
import geektime.im.lecture.entity.ImGroupMsg;
import geektime.im.lecture.entity.ImMsgContact;
import geektime.im.lecture.entity.ImMsgContent;
import geektime.im.lecture.entity.ImUser;
import geektime.im.lecture.exceptions.BaseException;
import geektime.im.lecture.response.CommonEnum;
import geektime.im.lecture.service.UserService;
import geektime.im.lecture.vo.MessageContactVO;
import geektime.im.lecture.vo.UserVo;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private ImGroupMsgMapper groupMsgMapper;

    @Override
    public UserVo login(String email, String password) {
        ImUser user = userRepository.findByEmail(email);
        if (null == user) {
            log.warn("该用户不存在:" + email);
            throw new BaseException(CommonEnum.ACCOUNT_WRONG);
        }
        if (user.getPassword().equals(password)) {
            log.info(user.getUsername() + " logged in!");
        } else {
            log.warn(user.getUsername() + " failed to log in!");
            throw new BaseException(CommonEnum.ACCOUNT_WRONG);
        }
        UserVo userVo = new UserVo();
        userVo.setUid(user.getUid());
        userVo.setUsername(user.getUsername());
        userVo.setAvatar(user.getAvatar());
        userVo.setEmail(user.getEmail());
        return userVo;
    }


    @Override
    public List<ImUser> getAllUsersExcept(String exceptUid) {
        List<ImUser> otherUsers = userRepository.findUsersByUidIsNot(exceptUid);
        return otherUsers;
    }

    @Override
    public List<ImUser> getAllUsersExcept(ImUser exceptUser) {
        List<ImUser> otherUsers = userRepository.findUsersByUidIsNot(exceptUser.getUid());
        return otherUsers;
    }


    @Override
    public ImUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ImUser getUserByUid(String uid) {
        return userRepository.findByUid(uid);
    }

    @Override
    public UserVo register(String email, String password, String name) {
        if (null != userRepository.findByEmail(email)) {
            throw new BaseException(CommonEnum.ACCOUNT_EXIST);
        }
        ImUser user = new ImUser();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatar("lisi.png");
        userRepository.insert(user);
        UserVo userVo = new UserVo();
        userVo.setEmail(email);
        userVo.setUsername(name);
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }
}
