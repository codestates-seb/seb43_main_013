package com.CreatorConnect.server.member.bookmark.repository;

import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
