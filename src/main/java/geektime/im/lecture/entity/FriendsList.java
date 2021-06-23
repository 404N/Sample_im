package geektime.im.lecture.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "friends_list")
public class FriendsList {
    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "friend_id")
    private String friendId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return owner_id
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return friend_id
     */
    public String getFriendId() {
        return friendId;
    }

    /**
     * @param friendId
     */
    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}