package geektime.im.lecture.service.impl;

import geektime.im.lecture.dao.ImGroupInfoMapper;
import geektime.im.lecture.dao.ImGroupMemberMapper;
import geektime.im.lecture.dao.ImGroupMsgMapper;
import geektime.im.lecture.entity.ImGroupInfo;
import geektime.im.lecture.entity.ImGroupMsg;
import geektime.im.lecture.entity.ImUser;
import geektime.im.lecture.service.GroupService;
import geektime.im.lecture.vo.GroupMsgVo;
import geektime.im.lecture.vo.GroupVo;
import geektime.im.lecture.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GroupServiceImpl implements GroupService {

    @Autowired
    private ImGroupInfoMapper groupInfoRepository;

    @Autowired
    private ImGroupMsgMapper groupMsgMapper;

    @Autowired
    private ImGroupMemberMapper groupMemberMapper;

    @Override
    public void createGroup(String userId, String groupName) {
        ImGroupInfo groupInfo=new ImGroupInfo();
        groupInfo.setGroupUserId(userId);
        groupInfo.setGroupName(groupName);
        groupInfo.setCreateTime(new Date());
        groupInfoRepository.insert(groupInfo);
    }



    @Override
    public List<GroupVo> queryGroup(String groupName) {
        return groupInfoRepository.queryGroupByName(groupName);
    }

    @Override
    public List<GroupMsgVo> queryMsgById(String groupId) {
        List<ImGroupMsg> msgList=groupMsgMapper.queryMsgByGroupId(groupId);
        List<GroupMsgVo> groupMsgVos=new ArrayList<>();
        msgList.forEach(msg->{
            GroupMsgVo vo=new GroupMsgVo();
            vo.setGroupId(groupId);
            vo.setAvatar(msg.getSendAvatar());
            vo.setCreateTime(msg.getSendTime());
            vo.setContent(msg.getContent());
            vo.setMid(msg.getMid());
            vo.setOwnerName(msg.getSendName());
            vo.setOwnerUid(msg.getSendId());
            groupMsgVos.add(vo);
        });
        return groupMsgVos;
    }

    @Override
    public List<GroupMsgVo> queryGroupMsgByMid(String groupId, Integer mid) {
        List<ImGroupMsg> msgList=groupMsgMapper.queryGroupMsgByMid(groupId,mid);
        List<GroupMsgVo> groupMsgVos=new ArrayList<>();
        msgList.forEach(msg->{
            GroupMsgVo vo=new GroupMsgVo();
            vo.setGroupId(groupId);
            vo.setAvatar(msg.getSendAvatar());
            vo.setCreateTime(msg.getSendTime());
            vo.setContent(msg.getContent());
            vo.setMid(msg.getMid());
            vo.setOwnerName(msg.getSendName());
            vo.setOwnerUid(msg.getSendId());
            groupMsgVos.add(vo);
        });
        return groupMsgVos;
    }

    @Override
    public List<ImUser> queryUsersByGroupId(String groupId) {
        return groupMemberMapper.queryUsersByGroupId(groupId);
    }

    @Override
    public ImGroupInfo queryGroupByGroupId(String groupId) {
        return groupInfoRepository.queryGroupByGroupId(groupId);
    }
}
