package com.CreatorConnect.server.board.jobboard.repository;

import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobBoardRepository extends JpaRepository<JobBoard, Long> {
    @Query("select j from JobBoard j where j.jobCategory.jobCategoryId = :jobCategoryId")
    Page<JobBoard> findJobBoardsByCategoryId(@Param("jobCategoryId") Long jobCategoryId, Pageable pageable);

    Page<JobBoard> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
