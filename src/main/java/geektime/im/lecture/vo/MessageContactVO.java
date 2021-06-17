package geektime.im.lecture.vo;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class MessageContactVO {
    private Integer ownerUid;
    private String ownerAvatar;
    private String ownerName;
    private Integer totalUnread;
    private List<ContactInfo> contactInfoList;

    public MessageContactVO(Integer ownerUid, String ownerName, String ownerAvatar, Integer totalUnread) {
        this.ownerUid = ownerUid;
        this.ownerAvatar = ownerAvatar;
        this.ownerName = ownerName;
        this.totalUnread = totalUnread;
    }

    public class ContactInfo {
        private Integer otherUid;//联系人id或群聊id
        private String otherName;
        private String otherAvatar;
        private Integer mid;
        private Integer type; //2表示群聊
        private String content;

        public Integer getConvUnread() {
            return convUnread;
        }

        private Integer convUnread;
        private Date createTime;

        public ContactInfo(Integer otherUid, String otherName, String otherAvatar, Integer mid, Integer type, String content, Integer convUnread, Date createTime) {
            this.otherUid = otherUid;
            this.otherName = otherName;
            this.otherAvatar = otherAvatar;
            this.mid = mid;
            this.type = type;
            this.content = content;
            this.convUnread = convUnread;
            this.createTime = createTime;
        }

        public Integer getOtherUid() {
            return otherUid;
        }

        public Integer getMid() {
            return mid;
        }

        public Integer getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        public String getOtherName() {
            return otherName;
        }

        public String getOtherAvatar() {
            return otherAvatar;
        }

        public Date getCreateTime() {
            return createTime;
        }
    }

    public void setContactInfoList(List<ContactInfo> contactInfoList) {
        this.contactInfoList = contactInfoList;
    }

    public void appendContact(ContactInfo contactInfo) {
        if (contactInfoList != null) {
        } else {
            contactInfoList = Lists.newArrayList();
        }
        contactInfoList.add(contactInfo);
    }

    public Integer getOwnerUid() {
        return ownerUid;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Integer getTotalUnread() {
        return totalUnread;
    }

    public List<ContactInfo> getContactInfoList() {
        return contactInfoList;
    }
}
