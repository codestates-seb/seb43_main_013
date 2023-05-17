package com.CreatorConnect.server.board.recomments.freerecomment.service;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.freecomment.entity.FreeComment;
import com.CreatorConnect.server.board.comments.freecomment.repository.FreeCommentRepository;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.common.service.ReCommentService;
import com.CreatorConnect.server.board.recomments.freerecomment.entity.FreeReComment;
import com.CreatorConnect.server.board.recomments.freerecomment.mapper.FreeReCommentMapper;
import com.CreatorConnect.server.board.recomments.freerecomment.repository.FreeReCommentRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeReCommentServiceImpl implements ReCommentService{
    private final FreeCommentRepository freeCommentRepository;
    private final FreeReCommentRepository freeReCommentRepository;
    private final FreeReCommentMapper mapper;
    private final MemberService memberService;

    @Override
    public ReCommentResponseDto.Post createReComment(Long freeBoardId, Long commentId, ReCommentDto.Post postdto) {
        // 댓글 찾기
        Optional<FreeComment> freeComment = freeCommentRepository.findById(new CommentPK(freeBoardId,commentId));
        FreeComment foundFreeComment = freeComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        // dto to Entity
        FreeReComment freeReComment = mapper.dtoToFreeReComment(postdto, foundFreeComment);

        // Entity to dto
        ReCommentResponseDto.Post post = new ReCommentResponseDto.Post();
        post.setRecommentId(freeReComment.getReCommentPK().getReCommentId());
        // response
        return  post;
    }
    @Override
    public void updateReComment(String token, Long freeBoardId, Long commentId, Long reCommentId, CommentDto.Patch patchDto){

        // Dto의 Id값으로 Entity찾기
        FreeReComment foundFreeReComment = findVerifiedFreeReComment(freeBoardId, commentId, reCommentId);

        // 멤버 검증
        Member findMember = memberService.findVerifiedMember(foundFreeReComment.getMemberId());
        memberService.verifiedAuthenticatedMember(token, findMember);

        //찾은 Entity의 값 변경
        Optional.ofNullable(patchDto.getContent())
                .ifPresent(foundFreeReComment::setContent);

        // 저장
        freeReCommentRepository.save(foundFreeReComment);
    }

    // 대댓글 조회
    @Override
    public  ReCommentResponseDto.Details responseReComment(Long freeBoardId, Long commentId, Long reCommentId){
        // 클라이언트에서 보낸 ID값으로 Entity 조회
        FreeReComment foundFreeReComment = findVerifiedFreeReComment(freeBoardId, commentId, reCommentId);
        //entity -> dto 매핑, 리턴
        return mapper.freeReCommentToReCommentDetailsResponse(foundFreeReComment);
    }
    @Override
    public void deleteReComment(String token, Long freeBoardId, Long commentId, Long reCommentId){
        // 대댓글 찾기
        FreeReComment foundReComment = findVerifiedFreeReComment(freeBoardId, commentId, reCommentId);

        // 멤버 검증
        Member findMember = memberService.findVerifiedMember(foundReComment.getMemberId());
        memberService.verifiedAuthenticatedMember(token, findMember);

        // 대댓글 수 -1
        Long reCommentCount = foundReComment.getFreeComment().getReCommentCount();
        foundReComment.getFreeComment().setReCommentCount(reCommentCount - 1);

        // 삭제
        freeReCommentRepository.delete(foundReComment);
    }

    // 피드백 대댓글 찾는 메서드
    private FreeReComment findVerifiedFreeReComment(Long freeBoardId, Long commentId, Long reCommentId) {
        Optional<FreeReComment> freeReComment = freeReCommentRepository.findById(new ReCommentPK(new CommentPK(freeBoardId, commentId),reCommentId));
        return freeReComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RE_COMMENT_NOT_FOUND));
    }
}