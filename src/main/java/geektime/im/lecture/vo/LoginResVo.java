package geektime.im.lecture.vo;


import geektime.im.lecture.entity.AddFriendRequest;
import geektime.im.lecture.entity.ImUser;
import org.apache.catalina.User;

import java.util.List;

public class LoginResVo {
    private UserVo loginUser;
    private List<UserVo> otherUsers;
    private MessageContactVO contactVO;
    private List<AddFriendRequest> addFriendRequestList;

    public UserVo getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(UserVo loginUser) {
        this.loginUser = loginUser;
    }

    public List<UserVo> getOtherUsers() {
        return otherUsers;
    }

    public void setOtherUsers(List<UserVo> otherUsers) {
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
