package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.ImGroupInfo;
import geektime.im.lecture.entity.ImGroupMsg;
import geektime.im.lecture.vo.GroupVo;
import geektime.im.lecture.vo.MessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ImGroupInfoMapper extends CustomerMapper<ImGroupInfo> {

    /**
     * 根据群名模糊查找群
     * @param groupName
     * @return
     */
    List<GroupVo> queryGroupByName(String groupName);

    ImGroupInfo queryGroupByGroupId(String groupId);

}