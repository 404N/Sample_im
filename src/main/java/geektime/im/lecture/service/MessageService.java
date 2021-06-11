package geektime.im.lecture.service;


import geektime.im.lecture.entity.ImUser;
import geektime.im.lecture.vo.LoginResVo;
import geektime.im.lecture.vo.MessageContactVO;
import geektime.im.lecture.vo.MessageVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    /**
     * 发新消息
     *
     * @param senderUid
     * @param recipientUid
     * @param content
     * @param msgType
     * @return
     */
    MessageVO sendNewMsg(Integer senderUid, Integer recipientUid, String content, Integer msgType);

    /**
     * 查两人的历史消息
     * @param ownerUid
     * @param otherUid
     * @return
     */
    List<MessageVO> queryConversationMsg(Integer ownerUid, Integer otherUid);

    /**
     * 查询两人从某一条消息开始的新消息
     * @param ownerUid
     * @param otherUid
     * @param fromMid
     * @return
     */
    List<MessageVO> queryNewerMsgFrom(Integer ownerUid, Integer otherUid, Integer fromMid);

    /**
     * 查询某个用户的最近联系人
     * @param ownerUid
     * @return
     */
    MessageContactVO queryContacts(Integer ownerUid);

    /**
     * 查询某人总未读
     * @param ownerUid
     * @return
     */
    Integer queryTotalUnread(Integer ownerUid);

    /**
     * 根据邮箱查找用户登陆之后的首页信息
     * @param email
     * @return
     */
    LoginResVo queryLoginData(Integer uid);

    /**
     * 查询该群最新的50条消息
     * @param groupId
     * @return
     */
    List<MessageVO> queryGroupMsg(Integer groupId);
}
