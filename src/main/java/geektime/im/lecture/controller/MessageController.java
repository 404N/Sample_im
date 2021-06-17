package geektime.im.lecture.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import geektime.im.lecture.response.ResultBody;
import geektime.im.lecture.response.ResultUtil;
import geektime.im.lecture.service.MessageService;
import geektime.im.lecture.vo.MessageContactVO;
import geektime.im.lecture.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;


    @PostMapping(path = "/sendMsg")
    public ResultBody sendMsg(@RequestParam String senderUid, @RequestParam String recipientUid, String content, Integer msgType) {
        MessageVO messageContent = messageService.sendNewMsg(senderUid, recipientUid, content, msgType);
        if (null != messageContent) {
            return ResultUtil.success(JSONObject.toJSONString(messageContent));
        } else {
            return ResultUtil.success();
        }
    }

    @GetMapping(path = "/queryMsg")
    public ResultBody queryMsg(@RequestParam String ownerUid, @RequestParam String otherUid) {
        List<MessageVO> messageVO = messageService.queryConversationMsg(ownerUid, otherUid);
        if (messageVO != null) {
            return ResultUtil.success(JSONArray.toJSONString(messageVO));
        } else {
            return ResultUtil.success();
        }
    }

    @GetMapping(path = "/queryMsgSinceMid")
    public ResultBody queryMsgSinceMid(@RequestParam String ownerUid, @RequestParam String otherUid, @RequestParam Integer lastMid) {
        List<MessageVO> messageVO = messageService.queryNewerMsgFrom(ownerUid, otherUid, lastMid);
        if (messageVO != null) {
            return ResultUtil.success(JSONArray.toJSONString(messageVO));
        } else {
            return ResultUtil.success();
        }
    }

}
