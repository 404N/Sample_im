package geektime.im.lecture.vo;

import java.util.Date;

public class MessageVO {
    private Integer mid;
    private String content;
    private String ownerUid;
    private Integer type;
    private String otherUid;
    private Date createTime;
    private String ownerUidAvatar;
    private String otherUidAvatar;
    private String ownerName;
    private String otherName;

    public String getOwnerName() {
        return ownerName;
    }

    public String getOtherName() {
        return otherName;
    }

    public MessageVO(Integer mid, String content, String ownerUid, Integer type, String otherUid, Date createTime, String ownerUidAvatar, String otherUidAvatar, String ownerName, String otherName) {
        this.mid = mid;
        this.content = content;
        this.ownerUid = ownerUid;
        this.type = type;
        this.otherUid = otherUid;
        this.createTime = createTime;
        this.ownerUidAvatar = ownerUidAvatar;
        this.otherUidAvatar = otherUidAvatar;
        this.ownerName = ownerName;
        this.otherName = otherName;
    }

    public Integer getMid() {
        return mid;
    }

    public String getContent() {
        return content;
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public Integer getType() {
        return type;
    }

    public String getOtherUid() {
        return otherUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getOwnerUidAvatar() {
        return ownerUidAvatar;
    }

    public String getOtherUidAvatar() {
        return otherUidAvatar;
    }


}
