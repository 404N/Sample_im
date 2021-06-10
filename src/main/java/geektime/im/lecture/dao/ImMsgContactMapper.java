package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.ImMsgContact;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ImMsgContactMapper extends CustomerMapper<ImMsgContact> {

    /**
     * 通过uid查找联系人
     * @param ownerUid
     * @return
     */
    List<ImMsgContact> findMessageContactsByOwnerUid(Integer ownerUid);

    /**
     * 通过发送id和接收id查找关系
     * @param ownerUid
     * @param otherId
     * @return
     */
    ImMsgContact findMsgByOwnerIdAndOtherId(Integer ownerUid,Integer otherId);
}