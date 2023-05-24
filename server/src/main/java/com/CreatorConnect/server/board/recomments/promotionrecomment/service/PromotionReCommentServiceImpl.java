package com.CreatorConnect.server.board.recomments.promotionrecomment.service;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.promotioncomment.entity.PromotionComment;
import com.CreatorConnect.server.board.comments.promotioncomment.repository.PromotionCommentRepository;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.common.service.ReCommentService;
import com.CreatorConnect.server.board.recomments.promotionrecomment.entity.PromotionReComment;
import com.CreatorConnect.server.board.recomments.promotionrecomment.mapper.PromotionReCommentMapper;
import com.CreatorConnect.server.board.recomments.promotionrecomment.repository.PromotionReCommentRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionReCommentServiceImpl implements ReCommentService {
    private final PromotionCommentRepository promotionCommentRepository;
    private final PromotionReCommentRepository promotionReCommentRepository;
    private final PromotionReCommentMapper mapper;
    private final MemberService memberService;

    @Override
    public ReCommentResponseDto.Post createReComment(Long promotionBoardId, Long commentId, ReCommentDto.Post postdto) {
        // 멤버 검증
        memberService.verifiedAuthenticatedMember(postdto.getMemberId());

        // 댓글 찾기
        Optional<PromotionComment> promotionComment = promotionCommentRepository.findById(new CommentPK(promotionBoardId,commentId));
        PromotionComment foundPromotionComment = promotionComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        // dto to Entity
        PromotionReComment promotionReComment = mapper.dtoToPromotionReComment(postdto, foundPromotionComment);

        // Entity to dto
        ReCommentResponseDto.Post post = new ReCommentResponseDto.Post();
        post.setRecommentId(promotionReComment.getReCommentPK().getReCommentId());

        // response
        return  post;
    }
    @Override
    public void updateReComment(Long promotionBoardId, Long commentId, Long reCommentId, CommentDto.Patch patchDto){

        // Dto의 Id값으로 Entity찾기
        PromotionReComment foundPromotionReComment = findVerifiedPromotionReComment(promotionBoardId, commentId, reCommentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(patchDto.getMemberId());

        //찾은 Entity의 값 변경
        Optional.ofNullable(patchDto.getContent())
                .ifPresent(foundPromotionReComment::setContent);

        // 저장
        promotionReCommentRepository.save(foundPromotionReComment);
    }

    // 대댓글 조회
    @Override
    public  ReCommentResponseDto.Details responseReComment(Long promotionBoardId, Long commentId, Long reCommentId){
        // 클라이언트에서 보낸 ID값으로 Entity 조회
        PromotionReComment foundPromotionReComment = findVerifiedPromotionReComment(promotionBoardId, commentId, reCommentId);
        // entity -> dto 매핑, 리턴
        return mapper.promotionReCommentToReCommentDetailsResponse(foundPromotionReComment);
    }
    @Override
    public void deleteReComment(Long promotionBoardId, Long commentId, Long reCommentId){
        // 대댓글 찾기
        PromotionReComment foundReComment = findVerifiedPromotionReComment(promotionBoardId, commentId, reCommentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(foundReComment.getMemberId());

        // 대댓글 수 -1
        Long reCommentCount = foundReComment.getPromotionComment().getReCommentCount();
        foundReComment.getPromotionComment().setReCommentCount(reCommentCount - 1);

        // 삭제
        promotionReCommentRepository.delete(foundReComment);
    }

    // 대댓글 찾는 메서드
    private PromotionReComment findVerifiedPromotionReComment(Long promotionBoardId, Long commentId, Long reCommentId) {
        Optional<PromotionReComment> promotionReComment = promotionReCommentRepository.findById(new ReCommentPK(new CommentPK(promotionBoardId, commentId),reCommentId));
        return promotionReComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RE_COMMENT_NOT_FOUND));
    }
}
