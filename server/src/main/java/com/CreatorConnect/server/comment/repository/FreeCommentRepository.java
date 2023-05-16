package com.CreatorConnect.server.comment.repository;

import com.CreatorConnect.server.comment.entity.CommentPK;
import com.CreatorConnect.server.comment.entity.FreeComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeCommentRepository extends JpaRepository<FreeComment, CommentPK> {
    @Query("select f from FreeComment f where f.freeBoard.freeBoardId = :freeBoardId")
    Page<FreeComment> findByFreeBoardId(@Param("freeBoardId") long freeBoardId, Pageable pageable);

}
