package geektime.im.lecture.service;

import geektime.im.lecture.entity.ImUser;
import geektime.im.lecture.vo.MessageContactVO;
import geektime.im.lecture.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserVo login(String email, String password);

    List<ImUser> getAllUsersExcept(Integer exceptUid);

    List<ImUser> getAllUsersExcept(ImUser exceptUser);

    MessageContactVO getContacts(ImUser ownerUser);

    ImUser getUserByEmail(String email);

    ImUser getUserByUid(Integer uid);

    UserVo register(String email, String password, String name);
}
