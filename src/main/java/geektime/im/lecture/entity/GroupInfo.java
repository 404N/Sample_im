package geektime.im.lecture.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "im_group_info", schema = "im")
public class GroupInfo {
    private Long groupId;
    private String groupName;
    private Long groupUserId;
    private Integer groupNewsId;
    private Date createTime;

    @Id
    @Column(name = "group_id")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "group_user_id")
    public Long getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(Long groupUserId) {
        this.groupUserId = groupUserId;
    }

    @Basic
    @Column(name = "group_news_id")
    public Integer getGroupNewsId() {
        return groupNewsId;
    }

    public void setGroupNewsId(Integer groupNewsId) {
        this.groupNewsId = groupNewsId;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupInfo that = (GroupInfo) o;
        return groupId == that.groupId && groupUserId == that.groupUserId && Objects.equals(groupName, that.groupName) && Objects.equals(groupNewsId, that.groupNewsId) && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName, groupUserId, groupNewsId, createTime);
    }
}
