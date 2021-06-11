package geektime.im.lecture.service;

import geektime.im.lecture.vo.GroupMsgVo;
import geektime.im.lecture.vo.GroupVo;
import geektime.im.lecture.vo.MessageVO;
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

    /**
     * 查找群聊消息根据页数
     * @param groupId
     * @param page
     * @return
     */
    List<GroupMsgVo> queryMsgById(Integer groupId);
}
