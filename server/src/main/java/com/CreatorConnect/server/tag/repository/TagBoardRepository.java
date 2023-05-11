package com.CreatorConnect.server.tag.repository;

import com.CreatorConnect.server.tag.entity.Tag;
import com.CreatorConnect.server.tag.entity.TagBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagBoardRepository extends JpaRepository<TagBoard, Long> {
    Optional<TagBoard> findByTag(Tag tag);
}
