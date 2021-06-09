package geektime.im.lecture.service;

import geektime.im.lecture.vo.GroupVo;

import java.util.List;

public interface GroupService {
    /**
     * 创建群聊
     * @param userId
     * @param groupName
     */
    void createGroup(Long userId,String groupName);

    /**
     * 查询群聊接口
     * @param userId
     * @param groupName
     */
    List<GroupVo> queryGroup(String groupName);
}
