package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.ImGroupMsg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface ImGroupMsgMapper extends CustomerMapper<ImGroupMsg> {
}