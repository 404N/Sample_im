package geektime.im.lecture.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "im_group_member", schema = "im")
public class GroupMember {
    private Long id;
    private Long groupId;
    private Long userId;
    private Timestamp joinTime;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_id")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "join_time")
    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupMember that = (GroupMember) o;
        return id == that.id && groupId == that.groupId && userId == that.userId && Objects.equals(joinTime, that.joinTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, userId, joinTime);
    }
}
