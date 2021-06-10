package geektime.im.lecture.vo;


import geektime.im.lecture.entity.ImUser;

import java.util.List;

public class LoginResVo {
    private ImUser loginUser;
    private List<ImUser> otherUsers;
    private MessageContactVO contactVO;

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
}
