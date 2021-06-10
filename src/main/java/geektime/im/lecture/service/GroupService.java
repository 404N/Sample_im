package geektime.im.lecture.service;

import geektime.im.lecture.vo.GroupVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    /**
     * 创建群聊
     * @param userId
     * @param groupName
     */
    void createGroup(Integer userId,String groupName);

    /**
     * 查询群聊接口
     * @param groupName
     */
    List<GroupVo> queryGroup(String groupName);
}
