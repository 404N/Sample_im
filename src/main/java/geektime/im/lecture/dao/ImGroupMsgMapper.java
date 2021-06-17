package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.ImGroupMsg;
import geektime.im.lecture.entity.ImMsgContent;
import geektime.im.lecture.entity.ImUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ImGroupMsgMapper extends CustomerMapper<ImGroupMsg> {
    /**
     * 根据群id查找所有消息
     * @param groupId
     * @return
     */
    List<ImGroupMsg> queryMsgByGroupId(String groupId);

    /**
     * 查找mid大于目标值的所有群消息
     * @param groupId
     * @param mid
     * @return
     */
    List<ImGroupMsg> queryGroupMsgByMid(String groupId, Integer mid);

    /**
     * 插入一条群聊消息，并返回mid
     * @param content
     * @return
     */
    Integer insertGetMid(@Param("content") ImGroupMsg content);

}