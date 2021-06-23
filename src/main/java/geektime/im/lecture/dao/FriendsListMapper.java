package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.FriendsList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendsListMapper extends CustomerMapper<FriendsList> {

    /**
     * 查找是否是好友
     * @param ownerUid
     * @param otherUid
     * @return
     */
    FriendsList selectByOwnerIdAndFriendId(String ownerUid, String otherUid);
}