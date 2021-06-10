package geektime.im.lecture.service.impl;

import geektime.im.lecture.dao.ImGroupInfoMapper;
import geektime.im.lecture.entity.ImGroupInfo;
import geektime.im.lecture.service.GroupService;
import geektime.im.lecture.vo.GroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
