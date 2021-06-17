package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.ImGroupMember;
import geektime.im.lecture.entity.ImUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImGroupMemberMapper extends CustomerMapper<ImGroupMember> {
    /**
     * 查找群聊成员
     * @param groupId
     * @return
     */
    List<ImUser> queryUsersByGroupId(String groupId);
}