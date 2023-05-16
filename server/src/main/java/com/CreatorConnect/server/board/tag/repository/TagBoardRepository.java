package com.CreatorConnect.server.board.tag.repository;

import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.tag.entity.TagToFreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagBoardRepository extends JpaRepository<TagToFreeBoard, Long> {
//    Optional<TagToFreeBoard> findByTagToFreeBoard(FreeBoard freeBoard, Tag tag);

    List<TagToFreeBoard> findByFreeBoard(FreeBoard freeBoard);
}
