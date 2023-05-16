package com.CreatorConnect.server.comment.service;

import com.CreatorConnect.server.comment.dto.CommentDto;
import com.CreatorConnect.server.comment.dto.CommentResponseDto;
import com.CreatorConnect.server.comment.entity.CommentPK;
import com.CreatorConnect.server.comment.entity.FreeComment;
import com.CreatorConnect.server.comment.mapper.CommentMapper;
import com.CreatorConnect.server.comment.repository.FreeCommentRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.freeboard.repository.FreeBoardRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeCommentServiceImpl implements CommentService {
    private final FreeBoardRepository freeBoardRepository;
    private final FreeCommentRepository freeCommentRepository;
    private final CommentMapper mapper;
    private final MemberService memberService;

    // 댓글 등록
    @Override
    public CommentResponseDto.CommentContent createComment(Long id, CommentDto.Post postDto) {
        // 멤버 검증
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        memberService.verifiedAuthenticatedMember(postDto.getMemberId(), authentication.getName());

        //freeBoard찾기
        Optional<FreeBoard> freeBoard = freeBoardRepository.findById(id);
        FreeBoard foundFreeBoard = freeBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FREEBOARD_NOT_FOUND));

        //dto to Entity
        FreeComment freeComment = mapper.postDtoToFreeComment(id, postDto, foundFreeBoard);

        // entity to dto
        CommentResponseDto.CommentContent post = new CommentResponseDto.CommentContent();
        post.setCommentId(freeComment.getCommentPK().getCommentId());
        post.setContent( freeComment.getContent());
        return post;
    }

    //댓글 수정
    @Override
    public CommentResponseDto.CommentContent updateComment(Long freeBoardId, Long commentId, CommentDto.Patch patchDto) {
        // Dto의 Id값으로 Entity찾기
        FreeComment foundfreeComment = findVerifiedFreeComment(freeBoardId, commentId);

        // 멤버 검증
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        memberService.verifiedAuthenticatedMember(foundfreeComment.getMemberId(), authentication.getName());

        //찾은 Entity의 값 변경
        Optional.ofNullable(patchDto.getContent())
                .ifPresent(foundfreeComment::setContent);

        // 저장
        FreeComment updatedFreeComment = freeCommentRepository.save(foundfreeComment);

        // Entity-Dto 변환 후 리턴
        return new CommentResponseDto.CommentContent(updatedFreeComment.getCommentPK().getCommentId(), updatedFreeComment.getContent());
    }

    //댓글 조회
    @Override
    public CommentResponseDto.Details responseComment(Long freeBoardId, Long commentId) {
        // 클라이언트에서 보낸 ID값으로 Entity 조회
        FreeComment foundComment = findVerifiedFreeComment(freeBoardId, commentId);
        //entity -> dto 매핑, 리턴
        return mapper.freeCommentToCommentDetailsResponse(foundComment);
    }

    // 댓글 목록 조회
    @Override
    public CommentResponseDto.Multi<CommentResponseDto.Details> responseComments(Long freeBoardId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("commentPK").descending());
        // Page 생성 - 최신순
        Page<FreeComment> freeCommentsPage = freeCommentRepository.findByFreeBoardId(freeBoardId, pageRequest);

        // 코맨트 리스트 가져오기
        List<CommentResponseDto.Details> responses = mapper.freeCommentsToCommentDetailsResponses(freeCommentsPage.getContent());

        // pageInfo 가져오기
        CommentResponseDto.PageInfo pageInfo = new CommentResponseDto.PageInfo(freeCommentsPage.getNumber() + 1, freeCommentsPage.getSize(), freeCommentsPage.getTotalElements(), freeCommentsPage.getTotalPages());

        // 리턴
        return new CommentResponseDto.Multi<>(responses, pageInfo);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long freeBoardId, Long commentId) {
        // 댓글 찾기
        FreeComment foundComment = findVerifiedFreeComment(freeBoardId, commentId);

        // 멤버 검증
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        memberService.verifiedAuthenticatedMember(foundComment.getMemberId(), authentication.getName());

        // 삭제
        freeCommentRepository.delete(foundComment);
    }

    // 자유게시판 댓글 찾는 메서드
    private FreeComment findVerifiedFreeComment(Long freeBoardId, Long commentId) {
        Optional<FreeComment> freeComment = freeCommentRepository.findById(new CommentPK(freeBoardId, commentId));
        return freeComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }

}
