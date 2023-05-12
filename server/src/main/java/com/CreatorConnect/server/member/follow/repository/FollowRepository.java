package com.CreatorConnect.server.member.follow.repository;

import com.CreatorConnect.server.member.follow.entity.Follow;
import com.CreatorConnect.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Long countByToMember(Long memberId); // 팔로워 수 (follower)
    Long countByFromMember(Long memberId); // 팔로우 수 (following)
}
