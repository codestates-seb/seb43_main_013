package com.CreatorConnect.server.tag.repository;

import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.tag.entity.TagToFreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagToFreeBoardRepository extends JpaRepository<TagToFreeBoard, Long> {
//    Optional<TagToFreeBoard> findByTagToFreeBoard(FreeBoard freeBoard, Tag tag);

    List<TagToFreeBoard> findByFreeBoard(FreeBoard freeBoard);
}
