package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.AddGroupRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddGroupRequestMapper extends CustomerMapper<AddGroupRequest> {

    /**
     * 根据用户id与群id查找是否有申请记录
     * @param uid
     * @param groupId
     * @return
     */
    AddGroupRequest selectByUidAndGroupId(String uid, String groupId);

    /**
     * 根据发送者id和群名称删除加群消息
     * @param sendId
     * @param groupId
     */
    void deleteBySendIdAndGroupId(String sendId, String groupId);

    /**
     * 根据uid查找加群请求
     * @param uid
     * @return
     */
    List<AddGroupRequest> queryAddGroupMsg(String uid);
}