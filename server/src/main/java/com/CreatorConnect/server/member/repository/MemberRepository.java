package com.CreatorConnect.server.member.repository;

import com.CreatorConnect.server.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByPhone(String phone);
    Page<Member> findByNicknameContaining(String keyword, Pageable pageable);
}
