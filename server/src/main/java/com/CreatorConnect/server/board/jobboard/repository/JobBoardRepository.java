package com.CreatorConnect.server.board.jobboard.repository;

import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobBoardRepository extends JpaRepository<JobBoard, Long> {
}
