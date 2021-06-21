package geektime.im.lecture.service;

import geektime.im.lecture.entity.ImUser;
import geektime.im.lecture.vo.MessageContactVO;
import geektime.im.lecture.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserVo login(String email, String password);

    List<ImUser> getAllUsersExcept(String exceptUid);

    List<ImUser> getAllUsersExcept(ImUser exceptUser);

    ImUser getUserByEmail(String email);

    ImUser getUserByUid(String uid);

    UserVo register(String email, String password, String name);

    /**
     * 根据用户名模糊查找用户
     * @param userName
     * @return
     */
    List<UserVo> queryUser(String userName);

    /**
     * 添加好友接口
     * @param ownerUid
     * @param otherUid
     */
    void addFriend(String ownerUid, String otherUid);
}
