package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.ImGroupMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImGroupMemberMapper extends CustomerMapper<ImGroupMember> {
}