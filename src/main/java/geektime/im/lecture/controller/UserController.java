package geektime.im.lecture.controller;

import geektime.im.lecture.entity.ImUser;
import geektime.im.lecture.response.ResultBody;
import geektime.im.lecture.response.ResultUtil;
import geektime.im.lecture.service.UserService;
import geektime.im.lecture.vo.LoginResVo;
import geektime.im.lecture.vo.MessageContactVO;
import geektime.im.lecture.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @RequestMapping(path = "/api/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody loginJson(@RequestParam String email, @RequestParam String password) {
        UserVo loginUser = userService.login(email, password);
        return ResultUtil.success(loginUser);
    }

    @RequestMapping(path = "/api/register",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody register(@RequestParam String email,@RequestParam String name, @RequestParam String password) {
        UserVo loginUser = userService.register(email, password,name);
        return ResultUtil.success(loginUser);
    }


}