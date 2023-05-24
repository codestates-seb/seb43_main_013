package com.CreatorConnect.server.board.comments.promotioncomment.service;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.common.service.CommentService;
import com.CreatorConnect.server.board.comments.promotioncomment.entity.PromotionComment;
import com.CreatorConnect.server.board.comments.promotioncomment.mapper.PromotionCommentMapper;
import com.CreatorConnect.server.board.comments.promotioncomment.repository.PromotionCommentRepository;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import com.CreatorConnect.server.board.promotionboard.repository.PromotionBoardRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class PromotionCommentServiceImpl implements CommentService  {
    private final PromotionBoardRepository promotionBoardRepository;
    private final PromotionCommentRepository promotionCommentRepository;
    private final PromotionCommentMapper mapper;
    private final MemberService memberService;

    // 댓글 등록
    @Override
    public CommentResponseDto.Post createComment(Long id, CommentDto.Post postDto) {
        // 멤버 검증
        memberService.verifiedAuthenticatedMember(postDto.getMemberId());

        // promotionBoard찾기
        Optional<PromotionBoard> promotionBoard = promotionBoardRepository.findById(id);
        PromotionBoard foundPromotion = promotionBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_NOT_FOUND));

        // dto to Entity
        PromotionComment promotionComment = mapper.dtoToPromotionComment(id, postDto, foundPromotion);

        // entity to dto
        CommentResponseDto.Post post = new CommentResponseDto.Post();
        post.setCommentId(promotionComment.getCommentPK().getCommentId());

        return post;
    }

    //댓글 수정
    @Override
    public void updateComment(Long promotionBoardId, Long commentId, CommentDto.Patch patchDto){

        // Dto의 Id값으로 Entity찾기
        PromotionComment foundpromotionComment = findVerifiedPromotionComment(promotionBoardId, commentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(foundpromotionComment.getMemberId());

        //찾은 Entity의 값 변경
        Optional.ofNullable(patchDto.getContent())
                .ifPresent(foundpromotionComment::setContent);

        promotionCommentRepository.save(foundpromotionComment);
    }

    // 댓글 조회
    @Override
    public  CommentResponseDto.Details responseComment(Long promotionBoardId, Long commentId){
        // 클라이언트에서 보낸 ID값으로 Entity 조회
        PromotionComment foundComment = findVerifiedPromotionComment(promotionBoardId, commentId);
        //entity -> dto 매핑, 리턴
        return mapper.promotionCommentToCommentDetailsResponse(foundComment);
    }

    // 댓글 목록 조회
    @Override
    public CommentResponseDto.Multi<CommentResponseDto.Details> responseComments(Long promotionBoardId, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("commentPK").ascending());
        // Page 생성 - 최신순
        Page<PromotionComment> promotionCommentsPage = promotionCommentRepository.findByPromotionBoardId(promotionBoardId, pageRequest);

        // 코맨트 리스트 가져오기
        List<CommentResponseDto.Details> responses = mapper.promotionCommentsToCommentDetailsResponses(promotionCommentsPage.getContent());

        // pageInfo 가져오기
        CommentResponseDto.PageInfo pageInfo = new CommentResponseDto.PageInfo(promotionCommentsPage.getNumber() + 1, promotionCommentsPage.getSize(), promotionCommentsPage.getTotalElements(), promotionCommentsPage.getTotalPages());

        return new CommentResponseDto.Multi<>(responses, pageInfo);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long promotionBoardId, Long commentId) {
        // 댓글 찾기
        PromotionComment foundComment = findVerifiedPromotionComment(promotionBoardId, commentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(foundComment.getMemberId());

        // 댓글 수 -1
        Long commentCount = foundComment.getPromotionBoard().getCommentCount();
        foundComment.getPromotionBoard().setCommentCount(commentCount - 1);

        promotionCommentRepository.delete(foundComment);
    }

    // 댓글 찾는 메서드
    private PromotionComment findVerifiedPromotionComment(Long promotionBoardId, Long commentId) {
        Optional<PromotionComment> promotionComment = promotionCommentRepository.findById(new CommentPK(promotionBoardId, commentId));
        return promotionComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }
}
