package geektime.im.lecture.service;

import geektime.im.lecture.entity.AddGroupRequest;
import geektime.im.lecture.entity.ImGroupInfo;
import geektime.im.lecture.entity.ImUser;
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
    void createGroup(String userId,String groupName);

    /**
     * 查询群聊接口
     * @param groupName
     */
    List<GroupVo> queryGroup(String groupName);

    /**
     * 查找群聊消息根据页数
     * @param groupId
     * @return
     */
    List<GroupMsgVo> queryMsgById(String groupId);

    /**
     * 查找大于mid的所有消息
     * @param groupId
     * @param mid
     * @return
     */
    List<GroupMsgVo> queryGroupMsgByMid(String groupId, Integer mid);

    /**
     * 查找群聊的所有用户
     * @param groupId
     * @return
     */
    List<String> queryUsersByGroupId(String groupId);

    /**
     * 通过群id查找群信息
     * @param groupId
     * @return
     */
    ImGroupInfo queryGroupByGroupId(String groupId);

    /**
     * 加入群聊请求
     * @param groupId
     * @param uid
     */
    void enterGroup(String groupId, String uid);

    /**
     * 根据uid查找加入群聊申请
     * @param uid
     * @return
     */
    List<AddGroupRequest> queryAddGroupMsg(String uid);
}
