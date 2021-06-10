package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.ImMsgRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ImMsgRelationMapper extends CustomerMapper<ImMsgRelation> {

    /**
     * 通过双方id查找聊天记录，根据消息id升序
     * @param ownerUid
     * @param otherUid
     * @return
     */
    List<ImMsgRelation> findAllByOwnerUidAndOtherUidOrderByMidAsc(Integer ownerUid, Integer otherUid);


    /**
     * 查找双方自mid之后的所有聊天
     * @param ownerUid
     * @param otherUid
     * @param lastMid
     * @return
     */
    List<ImMsgRelation> findAllByOwnerUidAndOtherUidSinceMid(Integer ownerUid, Integer otherUid, Integer lastMid);
}