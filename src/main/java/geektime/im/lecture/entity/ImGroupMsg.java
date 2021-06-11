package geektime.im.lecture.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "im_group_msg")
public class ImGroupMsg {
    @Id
    private Integer mid;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "send_id")
    private Integer sendId;

    @Column(name = "send_name")
    private String sendName;

    @Column(name = "send_avatar")
    private String sendAvatar;

    private String content;

    @Column(name = "send_time")
    private Date sendTime;

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
     * @return send_id
     */
    public Integer getSendId() {
        return sendId;
    }

    /**
     * @param sendId
     */
    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    /**
     * @return send_name
     */
    public String getSendName() {
        return sendName;
    }

    /**
     * @param sendName
     */
    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    /**
     * @return send_avatar
     */
    public String getSendAvatar() {
        return sendAvatar;
    }

    /**
     * @param sendAvatar
     */
    public void setSendAvatar(String sendAvatar) {
        this.sendAvatar = sendAvatar;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
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