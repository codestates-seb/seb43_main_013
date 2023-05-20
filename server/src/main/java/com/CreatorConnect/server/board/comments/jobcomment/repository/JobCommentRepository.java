package com.CreatorConnect.server.board.comments.jobcomment.repository;

import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.feedbackcomment.entity.FeedbackComment;
import com.CreatorConnect.server.board.comments.jobcomment.entity.JobComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobCommentRepository extends JpaRepository<JobComment, CommentPK> {
    @Query("select f from JobComment f where f.jobBoard.jobBoardId = :jobBoardId")
    Page<JobComment> findByJobBoardId(@Param("jobBoardId") long jobBoardId, Pageable pageable);
}
