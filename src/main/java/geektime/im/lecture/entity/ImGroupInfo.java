package geektime.im.lecture.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "im_group_info")
public class ImGroupInfo {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(generator = "UUID")
    private String groupId;

    @Column(name = "group_name")
    private String groupName;


    @Column(name = "group_avatar")
    private String groupAvatar;

    @Column(name = "group_user_id")
    private String groupUserId;

    @Column(name = "group_news_id")
    private Integer groupNewsId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return group_id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(String groupId) {
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
    public String getGroupUserId() {
        return groupUserId;
    }

    /**
     * @param groupUserId
     */
    public void setGroupUserId(String groupUserId) {
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

    public String getGroupAvatar() {
        return groupAvatar;
    }

    public void setGroupAvatar(String groupAvatar) {
        this.groupAvatar = groupAvatar;
    }
}