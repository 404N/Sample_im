package geektime.im.lecture.vo;

import geektime.im.lecture.entity.User;

import java.util.List;

public class LoginResVo {
    private User loginUser;
    private List<User> otherUsers;
    private MessageContactVO contactVO;

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public List<User> getOtherUsers() {
        return otherUsers;
    }

    public void setOtherUsers(List<User> otherUsers) {
        this.otherUsers = otherUsers;
    }

    public MessageContactVO getContactVO() {
        return contactVO;
    }

    public void setContactVO(MessageContactVO contactVO) {
        this.contactVO = contactVO;
    }
}
