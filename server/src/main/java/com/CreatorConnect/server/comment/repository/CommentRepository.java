package com.CreatorConnect.server.comment.repository;

import com.CreatorConnect.server.comment.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
