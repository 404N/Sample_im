package geektime.im.lecture.service.impl;

import com.alibaba.fastjson.JSONObject;
import geektime.im.lecture.Constants;
import geektime.im.lecture.dao.*;
import geektime.im.lecture.entity.*;
import geektime.im.lecture.exceptions.BaseException;
import geektime.im.lecture.response.CommonEnum;
import geektime.im.lecture.service.UserService;
import geektime.im.lecture.vo.MessageContactVO;
import geektime.im.lecture.vo.UserVo;
import geektime.im.lecture.ws.handler.WebsocketRouterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private WebsocketRouterHandler websocketRouterHandler;

    @Autowired
    private AddFriendRequestMapper addFriendRequestMapper;

    @Autowired
    private FriendsListMapper friendsListMapper;

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

    @Override
    public List<UserVo> queryUser(String userName) {
        List<UserVo> userVoList = new ArrayList<>();
        List<ImUser> users = userRepository.queryUser(userName);
        users.forEach(user -> {
            UserVo userVo = new UserVo();
            userVo.setUid(user.getUid());
            userVo.setUsername(user.getUsername());
            userVo.setEmail(user.getEmail());
            userVo.setAvatar(user.getAvatar());
            userVoList.add(userVo);
        });
        return userVoList;
    }

    @Override
    public void addFriend(String ownerUid, String otherUid) {
        AddFriendRequest request=addFriendRequestMapper.findById(ownerUid,otherUid);
        //若已发送过请求，则返回已发送过
        if(null!=request){
            throw new BaseException(CommonEnum.ADD_FRIEND_EXIST);
        }
        Date currentTime = new Date();
        AddFriendRequest addFriendRequest=new AddFriendRequest();
        ImUser imUser=userRepository.findByUid(ownerUid);
        if(null==imUser){
            throw new BaseException(CommonEnum.ACCOUNT_NOT_EXIST);
        }
        addFriendRequest.setOwnerId(ownerUid);
        addFriendRequest.setOwnerAvatar(imUser.getAvatar());
        addFriendRequest.setOwnerName(imUser.getUsername());
        addFriendRequest.setSendTime(currentTime);
        addFriendRequest.setOtherId(otherUid);
        addFriendRequest.setStatus(0);
        addFriendRequestMapper.insert(addFriendRequest);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", 10);
        jsonObject.put("data", JSONObject.toJSON(addFriendRequest));
        websocketRouterHandler.pushAddFriendMsg(ownerUid,otherUid,jsonObject);
    }

    @Override
    public void addFriendMsg(String ownerUid, String otherUid,Integer status) {
        if(null!=friendsListMapper.selectByOwnerIdAndFriendId(ownerUid,otherUid)){
            throw new BaseException(CommonEnum.FRIEND_EXIST);
        }
        Date currentTime = new Date();
        FriendsList friendsList=new FriendsList();
        friendsList.setOwnerId(ownerUid);
        friendsList.setFriendId(otherUid);
        friendsList.setCreateTime(currentTime);
        FriendsList friendsList2=new FriendsList();
        friendsList.setOwnerId(otherUid);
        friendsList.setFriendId(ownerUid);
        friendsList.setCreateTime(currentTime);
        //双向插入，便于查询
        friendsListMapper.insert(friendsList);
        friendsListMapper.insert(friendsList2);
    }

    @Override
    public List<AddFriendRequest> queryFriendRequests(String uid) {
        //根据uid查询所有好友请求
        List<AddFriendRequest> requestList=addFriendRequestMapper.selectByUid(uid);
        return requestList;
    }

    @Override
    public void updateFriendRequests(String uid) {
        //根据uid查询所有好友请求
        addFriendRequestMapper.updateByUid(uid);
    }
}
