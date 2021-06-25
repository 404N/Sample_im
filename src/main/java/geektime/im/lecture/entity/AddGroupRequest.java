package geektime.im.lecture.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "add_group_request")
public class AddGroupRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "send_id")
    private String sendId;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "send_time")
    private Date sendTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return send_id
     */
    public String getSendId() {
        return sendId;
    }

    /**
     * @param sendId
     */
    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

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
     * @return admin_id
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * @param adminId
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
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