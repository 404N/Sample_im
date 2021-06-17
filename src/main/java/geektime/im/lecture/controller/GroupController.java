package geektime.im.lecture.controller;

import geektime.im.lecture.response.ResultBody;
import geektime.im.lecture.response.ResultUtil;
import geektime.im.lecture.service.GroupService;
import geektime.im.lecture.vo.GroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping(path = "/createGroup")
    public ResultBody createGroup(@RequestParam String ownerUid, @RequestParam String groupName) {
        groupService.createGroup(ownerUid,groupName);
        return ResultUtil.success();
    }

    @GetMapping(path = "queryGroup")
    public ResultBody queryGroup(@RequestParam String groupName) {
        List<GroupVo> groupVoList=groupService.queryGroup(groupName);
        return ResultUtil.success(groupVoList);
    }
}
