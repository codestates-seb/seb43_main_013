package com.CreatorConnect.server.member.like.repository;

import com.CreatorConnect.server.member.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
