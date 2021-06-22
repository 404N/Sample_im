package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.AddFriendRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddFriendRequestMapper extends CustomerMapper<AddFriendRequest> {

    /**
     * 通过发送人id与好友id查找记录
     * @param ownerUid
     * @param otherUid
     * @return
     */
    AddFriendRequest findById(String ownerUid, String otherUid);

    /**
     * 将好友请求消息设置为已读
     * @param sendUid
     * @param recipientUid
     */
    void updateFriendRequest(String sendUid, String recipientUid);
}