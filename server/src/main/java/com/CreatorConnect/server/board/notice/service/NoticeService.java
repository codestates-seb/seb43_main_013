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

    /**
     * <공지사항 수정>
     * 1. 게시글 존재 여부 검증
     * 2. 게시글 작성자와 로그인한 멤버 비교
     * 3. 수정
     *  3-1. 제목 수정
     *  3-2. 내용 수정
     * 4. 저장
     */
    public Notice updateNotice(NoticeDto.Patch patch, Long noticeId) {
        // 1. 게시글 존재 여부 검증
        patch.setNoticeId(noticeId);
        Notice notice = mapper.noticePatchDtoToNotice(patch);
        Notice checkedNotice = verifyNotice(notice.getNoticeId());

        // 2. 게시글 작성자와 로그인한 멤버 비교
        memberService.verifiedAuthenticatedMember(checkedNotice.getMember().getMemberId());

        // 3. 수정
        // 3-1. 제목 수정
        Optional.ofNullable(notice.getTitle())
                .ifPresent(title -> checkedNotice.setTitle(title));

        // 3-2. 내용 수정
        Optional.ofNullable(notice.getContent())
                .ifPresent(content -> checkedNotice.setContent(content));

        return noticeRepository.save(checkedNotice);
    }

    // 게시글 검증 메서드
    private Notice verifyNotice(Long noticeId) {
        Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
        return optionalNotice.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.NOTICE_NOT_FOUND));
    }
}
