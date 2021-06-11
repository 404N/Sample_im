package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.ImMsgContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface ImMsgContentMapper extends CustomerMapper<ImMsgContent> {
    /**
     * 通过mid查找消息
     * @param mid
     * @return
     */
    ImMsgContent findByMid(Integer mid);

    /**
     *
     * @return
     */
    Integer insertGetMid(@Param("content")ImMsgContent content);
}