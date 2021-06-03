package geektime.im.lecture.response;

public class ResultUtil {
    public static ResultBody success(Object object) {
        ResultBody result = new ResultBody();
        result.setCode("200");
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static ResultBody success() {
        return success(null);
    }

    public static ResultBody error(CommonEnum commonEnum) {
        ResultBody result = new ResultBody();
        result.setCode(commonEnum.getResultCode());
        result.setMsg(commonEnum.getResultMsg());
        return result;
    }

    public static ResultBody error(String errorCode,String errorMsg) {
        ResultBody result = new ResultBody();
        result.setCode(errorCode);
        result.setMsg(errorMsg);
        return result;
    }
}
