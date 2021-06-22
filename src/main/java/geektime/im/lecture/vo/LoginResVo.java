package geektime.im.lecture.vo;


import geektime.im.lecture.entity.AddFriendRequest;
import geektime.im.lecture.entity.ImUser;

import java.util.List;

public class LoginResVo {
    private ImUser loginUser;
    private List<ImUser> otherUsers;
    private MessageContactVO contactVO;
    private List<AddFriendRequest> addFriendRequestList;

    public ImUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(ImUser loginUser) {
        this.loginUser = loginUser;
    }

    public List<ImUser> getOtherUsers() {
        return otherUsers;
    }

    public void setOtherUsers(List<ImUser> otherUsers) {
        this.otherUsers = otherUsers;
    }

    public MessageContactVO getContactVO() {
        return contactVO;
    }

    public void setContactVO(MessageContactVO contactVO) {
        this.contactVO = contactVO;
    }

    public List<AddFriendRequest> getAddFriendRequestList() {
        return addFriendRequestList;
    }

    public void setAddFriendRequestList(List<AddFriendRequest> addFriendRequestList) {
        this.addFriendRequestList = addFriendRequestList;
    }

    @Override
    public String toString() {
        return "LoginResVo{" +
                "loginUser=" + loginUser +
                ", otherUsers=" + otherUsers +
                ", contactVO=" + contactVO +
                '}';
    }
}
