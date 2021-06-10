package geektime.im.lecture.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "im_group_info")
public class ImGroupInfo {
    @Id
    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_user_id")
    private Integer groupUserId;

    @Column(name = "group_news_id")
    private Integer groupNewsId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return group_id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * @return group_name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return group_user_id
     */
    public Integer getGroupUserId() {
        return groupUserId;
    }

    /**
     * @param groupUserId
     */
    public void setGroupUserId(Integer groupUserId) {
        this.groupUserId = groupUserId;
    }

    /**
     * @return group_news_id
     */
    public Integer getGroupNewsId() {
        return groupNewsId;
    }

    /**
     * @param groupNewsId
     */
    public void setGroupNewsId(Integer groupNewsId) {
        this.groupNewsId = groupNewsId;
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