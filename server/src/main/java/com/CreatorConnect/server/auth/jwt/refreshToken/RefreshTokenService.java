package com.CreatorConnect.server.auth.jwt.refreshToken;

import com.CreatorConnect.server.auth.filter.JwtAuthenticationFilter;
import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, MemberRepository memberRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.memberRepository = memberRepository;
    }

    public boolean isValidRefreshToken(String refreshToken) {
        if (refreshToken != null && !refreshToken.isEmpty()) {
            RefreshToken storedRefreshToken = refreshTokenRepository.findByToken(refreshToken).get();
            return storedRefreshToken != null && refreshToken.equals(storedRefreshToken.getToken());
        }
        return false;
    }

    public Member findMemberByRefreshToken(String refreshToken){

        RefreshToken findRefreshToken = refreshTokenRepository.findByToken(refreshToken).get();
        Member member = memberRepository.findByEmail(findRefreshToken.getEmail()).get();

        return member;
    }

}
