package geektime.im.lecture.dao;

import geektime.im.lecture.entity.GroupMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMsgRepository extends JpaRepository<GroupMsg, Long> {
}
