package geektime.im.lecture.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "add_friend_request")
public class AddFriendRequest {
    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_avatar")
    private String ownerAvatar;

    @Column(name = "other_id")
    private String otherId;


    @Column(name = "send_time")
    private Date sendTime;

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
     * @return owner_name
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @param ownerName
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @return owner_avatar
     */
    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    /**
     * @param ownerAvatar
     */
    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    /**
     * @return other_id
     */
    public String getOtherId() {
        return otherId;
    }

    /**
     * @param otherId
     */
    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }


    /**
     * @return send_time
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}