package com.CreatorConnect.server.freeboard.repository;

import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
}
