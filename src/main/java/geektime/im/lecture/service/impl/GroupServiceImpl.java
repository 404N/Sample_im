package geektime.im.lecture.service.impl;

import geektime.im.lecture.dao.GroupInfoRepository;
import geektime.im.lecture.entity.GroupInfo;
import geektime.im.lecture.service.GroupService;
import geektime.im.lecture.vo.GroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupInfoRepository groupInfoRepository;

    @Override
    public void createGroup(Long userId, String groupName) {
        GroupInfo groupInfo=new GroupInfo();
        groupInfo.setGroupUserId(userId);
        groupInfo.setGroupName(groupName);
        groupInfo.setCreateTime(new Date());
        groupInfoRepository.save(groupInfo);
    }

    @Override
    public List<GroupVo> queryGroup(String groupName) {
        return groupInfoRepository.queryGroup(groupName);
    }
}
