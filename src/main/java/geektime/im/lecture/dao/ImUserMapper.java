package geektime.im.lecture.dao;

import geektime.im.lecture.core.CustomerMapper;
import geektime.im.lecture.entity.ImUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ImUserMapper extends CustomerMapper<ImUser> {

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    List<ImUser> findByEmail(String email);

    /**
     * 查找除当前用户之外的所有用户
     * @param uid
     * @return
     */
    List<ImUser> findUsersByUidIsNot(Integer uid);

    /**
     * 通过uid查找用户
     * @param uid
     * @return
     */
    ImUser findByUid(Integer uid);
}