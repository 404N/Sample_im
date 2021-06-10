package geektime.im.lecture.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "im_msg_content")
public class ImMsgContent {
    @Id
    private Integer mid;

    private String content;

    @Column(name = "sender_id")
    private Integer senderId;

    @Column(name = "recipient_id")
    private Integer recipientId;

    @Column(name = "msg_type")
    private Integer msgType;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return sender_id
     */
    public Integer getSenderId() {
        return senderId;
    }

    /**
     * @param senderId
     */
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    /**
     * @return recipient_id
     */
    public Integer getRecipientId() {
        return recipientId;
    }

    /**
     * @param recipientId
     */
    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    /**
     * @return msg_type
     */
    public Integer getMsgType() {
        return msgType;
    }

    /**
     * @param msgType
     */
    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
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