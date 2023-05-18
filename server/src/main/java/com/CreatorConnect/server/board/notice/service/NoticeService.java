package com.CreatorConnect.server.board.notice.service;

import com.CreatorConnect.server.board.notice.dto.NoticeDto;
import com.CreatorConnect.server.board.notice.entity.Notice;
import com.CreatorConnect.server.board.notice.mapper.NoticeMapper;
import com.CreatorConnect.server.board.notice.repository.NoticeRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberService memberService;
    private final NoticeMapper mapper;
    private final MemberRepository memberRepository;

    /**
     * <공지사항 등록>
     * 1. 작성자 검증
     * 2. 회원 매핑
     * 3. 등록
     */
    public Notice createNotice(NoticeDto.Post post) {
        Notice notice = mapper.noticePostDtoToNotice(post);

        // 1. 작성자 검증
        memberService.verifiedAuthenticatedMember(post.getMemberId());

        // 2. 회원 매핑
        Optional<Member> member = memberRepository.findById(post.getMemberId());
        notice.setMember(member.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)));

        // 3. 등록
        return noticeRepository.save(notice);


    }
}
