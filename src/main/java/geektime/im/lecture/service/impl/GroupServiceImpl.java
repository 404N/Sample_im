package geektime.im.lecture.service.impl;

import geektime.im.lecture.dao.ImGroupInfoMapper;
import geektime.im.lecture.entity.ImGroupInfo;
import geektime.im.lecture.entity.ImGroupMsg;
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

    @Override
    public void createGroup(Integer userId, String groupName) {
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
    public List<GroupMsgVo> queryMsgById(Integer groupId) {
        List<ImGroupMsg> msgList=groupInfoRepository.queryMsgByGroupId(groupId);
        List<GroupMsgVo> groupMsgVos=new ArrayList<>();
        msgList.forEach(msg->{
            GroupMsgVo vo=new GroupMsgVo();
            vo.setGroupId(groupId);

        });
        return groupMsgVos;
    }
}
