package geektime.im.lecture.response;


import geektime.im.lecture.exceptions.BaseError;

public enum CommonEnum implements BaseError {
    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("504","服务器正忙，请稍后再试!"),
    ACCOUNT_WRONG("1001","账号或密码错误!"),
    ACCOUNT_EXIST("1002","该邮箱已被注册!"),
    ACCOUNT_NOT_EXIST("1003","账号不存在!"),
    ADD_FRIEND_EXIST("1004","已发送过请求!"),
    NO_LOGIN("1005","账号登陆过期，重新登录!"),
    FRIEND_EXIST("1006","已经是好友!"),
    GROUP_NOT_EXIST("1007","群聊不存在!"),
    ;

    /** 错误码 */
    private String resultCode;

    /** 错误描述 */
    private String resultMsg;

    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

}