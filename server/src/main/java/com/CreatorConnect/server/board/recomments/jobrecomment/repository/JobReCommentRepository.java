package com.CreatorConnect.server.board.recomments.jobrecomment.repository;

import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.jobrecomment.entity.JobReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JobReCommentRepository extends JpaRepository<JobReComment, ReCommentPK> {
}
