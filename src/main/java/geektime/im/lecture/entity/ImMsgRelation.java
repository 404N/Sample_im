package geektime.im.lecture.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "im_msg_relation")
public class ImMsgRelation {
    @Id
    @Column(name = "owner_uid")
    private Integer ownerUid;

    @Id
    private Integer mid;

    @Column(name = "other_uid")
    private Integer otherUid;

    private Integer type;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return owner_uid
     */
    public Integer getOwnerUid() {
        return ownerUid;
    }

    /**
     * @param ownerUid
     */
    public void setOwnerUid(Integer ownerUid) {
        this.ownerUid = ownerUid;
    }

    /**
     * @return mid
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * @param mid
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }

    /**
     * @return other_uid
     */
    public Integer getOtherUid() {
        return otherUid;
    }

    /**
     * @param otherUid
     */
    public void setOtherUid(Integer otherUid) {
        this.otherUid = otherUid;
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
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