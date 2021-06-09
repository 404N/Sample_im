package geektime.im.lecture.dao;

import geektime.im.lecture.entity.GroupInfo;
import geektime.im.lecture.vo.GroupVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupInfoRepository extends JpaRepository<GroupInfo, Long> {

    @Query(value="select group_id,group_name from im_group_info a where a.group_name like %:groupName%")
    List<GroupVo> queryGroup(@Param("groupName") String groupName);
}
