package geektime.im.lecture.ws.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import geektime.im.lecture.entity.ImUser;
import geektime.im.lecture.service.MessageService;
import geektime.im.lecture.utils.EnhancedThreadFactory;
import geektime.im.lecture.vo.GroupMsgVo;
import geektime.im.lecture.vo.LoginResVo;
import geektime.im.lecture.vo.MessageVO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务端处理所有接收消息的handler，这里只是示例，没有拆分太细，建议实际项目中按消息类型拆分到不同的handler中。
 */
@ChannelHandler.Sharable
@Component
public class WebsocketRouterHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static final ConcurrentHashMap<Long, Channel> userChannel = new ConcurrentHashMap<>(15000);
    private static final ConcurrentHashMap<Channel, Long> channelUser = new ConcurrentHashMap<>(15000);
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(50, new EnhancedThreadFactory("ackCheckingThreadPool"));
    private static final Logger logger = LoggerFactory.getLogger(WebsocketRouterHandler.class);
    private static final AttributeKey<AtomicLong> TID_GENERATOR = AttributeKey.valueOf("tid_generator");
    private static final AttributeKey<ConcurrentHashMap> NON_ACKED_MAP = AttributeKey.valueOf("non_acked_map");

    @Autowired
    private MessageService messageService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            String msg = ((TextWebSocketFrame) frame).text();
            JSONObject msgJson = JSONObject.parseObject(msg);
            int type = msgJson.getIntValue("type");
            JSONObject data = msgJson.getJSONObject("data");
            switch (type) {
                case 0:
                    //心跳
                    long uid = data.getLong("uid");
                    long timeout = data.getLong("timeout");
                    logger.info("[heartbeat]: uid = {} , current timeout is {} ms, channel = {}", uid, timeout, ctx.channel());
                    ctx.writeAndFlush(new TextWebSocketFrame("{\"type\":0,\"timeout\":" + timeout + "}"));
                    break;
                case 1:
                    //上线消息，返回相应数据
                    long loginUid = data.getLong("uid");
                    userChannel.put(loginUid, ctx.channel());
                    channelUser.put(ctx.channel(), loginUid);
                    ctx.channel().attr(TID_GENERATOR).set(new AtomicLong(0));
                    ctx.channel().attr(NON_ACKED_MAP).set(new ConcurrentHashMap<Long, JSONObject>());
                    LoginResVo loginResVo = messageService.queryLoginData((int) loginUid);
                    logger.info("[user bind]: uid = {} , channel = {}", loginUid, ctx.channel());
                    JSONObject loginJson = new JSONObject();
                    loginJson.put("type", 1);
                    loginJson.put("status", "success");
                    loginJson.put("data", JSONArray.toJSON(loginResVo));
                    String str = loginJson.toJSONString();
                    ctx.writeAndFlush(new TextWebSocketFrame(str));
                    break;
                case 2:
                    //查询消息
                    Integer ownerUid = data.getInteger("ownerUid");
                    Integer otherUid = data.getInteger("otherUid");
                    List<MessageVO> messageVO = messageService.queryConversationMsg(ownerUid, otherUid);
                    String msgs = "";
                    if (messageVO != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", 2);
                        jsonObject.put("data", JSONArray.toJSON(messageVO));
                        msgs = jsonObject.toJSONString();
                    }
                    ctx.writeAndFlush(new TextWebSocketFrame(msgs));
                    break;

                case 3:
                    //发消息
                    Integer senderUid = data.getInteger("senderUid");
                    Integer recipientUid = data.getInteger("recipientUid");
                    String content = data.getString("content");
                    int msgType = data.getIntValue("msgType");
                    MessageVO messageContent = messageService.sendNewMsg(senderUid, recipientUid, content, msgType);
                    if (messageContent != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", 3);
                        jsonObject.put("data", JSONObject.toJSON(messageContent));
                        ctx.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(jsonObject)));
                    }
                    break;

                case 5:
                    //查总未读
                    Integer unreadOwnerUid = data.getInteger("uid");
                    long totalUnread = messageService.queryTotalUnread(unreadOwnerUid);
                    ctx.writeAndFlush(new TextWebSocketFrame("{\"type\":5,\"data\":{\"unread\":" + totalUnread + "}}"));
                    break;

                case 6:
                    //处理ack
                    long tid = data.getLong("tid");
                    ConcurrentHashMap<Long, JSONObject> nonAckedMap = ctx.channel().attr(NON_ACKED_MAP).get();
                    nonAckedMap.remove(tid);
                    break;

                case 100:
                    //群聊消息查询
                    //获取群id和消息id
                    Integer groupId = data.getInteger("groupId");
                    //type 0表示获取最新的50条，1表示获取自mid开始之后的消息
                    Integer getType = data.getInteger("type");
                    if (type == 0) {
                        //群消息记录页数
                        Integer page = data.getInteger("page");
                        //分页起始码以及每页页数
                        PageHelper.startPage(page, 50);
                        List<GroupMsgVo> messageVOList = messageService.queryGroupMsg(groupId);
                        PageInfo pageInfo = new PageInfo(messageVOList);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", 100);
                        jsonObject.put("data", JSONObject.toJSON(pageInfo));
                        jsonObject.put("page", page);
                        ctx.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(jsonObject)));
                    } else {
                        //无页码查询
                        Integer mid = data.getInteger("mid");
                        List<GroupMsgVo> messageVOList = messageService.queryGroupMsgByMid(groupId, mid);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", 100);
                        jsonObject.put("data", JSONObject.toJSON(messageVOList));
                        jsonObject.put("page", 0);
                        ctx.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(jsonObject)));
                    }

                case 101:
                    //群聊消息发送
                    //获取群id
                    Integer gId = data.getInteger("groupId");
                    Integer sId = data.getInteger("senderUid");
                    String groupContent = data.getString("content");
                    GroupMsgVo groupMsgVo = messageService.sendGroupMessage(sId, gId, groupContent);
                    if (groupMsgVo != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", 101);
                        jsonObject.put("data", JSONObject.toJSON(groupMsgVo));
                        ctx.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(jsonObject)));
                    }
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[channelActive]:remote address is {} ", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[channelClosed]:remote address is {} ", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("process error. uid is {},  channel info {}", channelUser.get(ctx.channel()), ctx.channel(), cause);
        ctx.channel().close();
    }

    public void pushMsg(long recipientUid, JSONObject message) {
        Channel channel = userChannel.get(recipientUid);
        if (channel != null && channel.isActive() && channel.isWritable()) {
            AtomicLong generator = channel.attr(TID_GENERATOR).get();
            long tid = generator.incrementAndGet();
            message.put("tid", tid);
            channel.writeAndFlush(new TextWebSocketFrame(message.toJSONString())).addListener(future -> {
                if (future.isCancelled()) {
                    logger.warn("future has been cancelled. {}, channel: {}", message, channel);
                } else if (future.isSuccess()) {
                    addMsgToAckBuffer(channel, message);
                    logger.warn("future has been successfully pushed. {}, channel: {}", message, channel);
                } else {
                    logger.error("message write fail, {}, channel: {}", message, channel, future.cause());
                }
            });
        }
    }

    public void pushGroupMsg(Integer groupId, Integer sendUid, JSONObject message) {
        List<ImUser> imUserList = messageService.queryUsersByGroupId(groupId);
        imUserList.forEach(user -> {
            if (!user.getUid().equals(sendUid)) {
                Channel channel = userChannel.get(user.getUid().longValue());
                if (channel != null && channel.isActive() && channel.isWritable()) {
                    AtomicLong generator = channel.attr(TID_GENERATOR).get();
                    long tid = generator.incrementAndGet();
                    message.put("tid", tid);
                    channel.writeAndFlush(new TextWebSocketFrame(message.toJSONString())).addListener(future -> {
                        if (future.isCancelled()) {
                            logger.warn("future has been cancelled. {}, channel: {}, groupId: {}", message, channel, groupId);
                        } else if (future.isSuccess()) {
                            addMsgToAckBuffer(channel, message);
                            logger.warn("future has been successfully pushed. {}, channel: {}, groupId: {}", message, channel, groupId);
                        } else {
                            logger.error("group message write fail, {}, channel: {}", message, channel, future.cause());
                        }
                    });
                }
            }
        });
    }

    /**
     * 清除用户和socket映射的相关信息
     *
     * @param channel
     */
    public void cleanUserChannel(Channel channel) {
        long uid = channelUser.remove(channel);
        userChannel.remove(uid);
        logger.info("[cleanChannel]:remove uid & channel info from gateway, uid is {}, channel is {}", uid, channel);
    }

    /**
     * 将推送的消息加入待ack列表
     *
     * @param channel
     * @param msgJson
     */
    public void addMsgToAckBuffer(Channel channel, JSONObject msgJson) {
        channel.attr(NON_ACKED_MAP).get().put(msgJson.getLong("tid"), msgJson);
        executorService.schedule(() -> {
            if (channel.isActive()) {
                checkAndResend(channel, msgJson);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    /**
     * 检查并重推
     *
     * @param channel
     * @param msgJson
     */
    private void checkAndResend(Channel channel, JSONObject msgJson) {
        long tid = msgJson.getLong("tid");
        //重推2次
        int tryTimes = 2;
        while (tryTimes > 0) {
            if (channel.attr(NON_ACKED_MAP).get().containsKey(tid) && tryTimes > 0) {
                channel.writeAndFlush(new TextWebSocketFrame(msgJson.toJSONString()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tryTimes--;
        }
    }
}
