package com.CreatorConnect.server.board.recomments.feedbackrecomment.service;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.feedbackcomment.entity.FeedbackComment;
import com.CreatorConnect.server.board.comments.feedbackcomment.repository.FeedbackCommentRepository;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.common.service.ReCommentService;
import com.CreatorConnect.server.board.recomments.feedbackrecomment.entity.FeedbackReComment;
import com.CreatorConnect.server.board.recomments.feedbackrecomment.mapper.FeedbackReCommentMapper;
import com.CreatorConnect.server.board.recomments.feedbackrecomment.repository.FeedbackReCommentRepository;
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
public class FeedbackReCommentServiceImpl implements ReCommentService {
    private final FeedbackCommentRepository feedbackCommentRepository;
    private final FeedbackReCommentRepository feedbackReCommentRepository;
    private final FeedbackReCommentMapper mapper;
    private final MemberService memberService;

    @Override
    public ReCommentResponseDto.Post createReComment(Long feedbackBoardId, Long commentId, ReCommentDto.Post postdto) {
        // 댓글 찾기
        Optional<FeedbackComment> feedbackComment = feedbackCommentRepository.findById(new CommentPK(feedbackBoardId,commentId));
        FeedbackComment foundFeedbackComment = feedbackComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        // dto to Entity
        FeedbackReComment feedbackReComment = mapper.dtoToFeedbackReComment(postdto, foundFeedbackComment);

        // Entity to dto
        ReCommentResponseDto.Post post = new ReCommentResponseDto.Post();
        post.setReCommentId(feedbackReComment.getReCommentPK().getReCommentId());
        // response
        return  post;
    }
    @Override
    public void updateReComment(String token, Long feedbackBoardId, Long commentId, Long reCommentId, CommentDto.Patch patchDto){

        // Dto의 Id값으로 Entity찾기
        FeedbackReComment foundFeedbackReComment = findVerifiedFeedbackReComment(feedbackBoardId, commentId, reCommentId);

        // 멤버 검증
        Member findMember = memberService.findVerifiedMember(foundFeedbackReComment.getMemberId());
        memberService.verifiedAuthenticatedMember(token, findMember);

        //찾은 Entity의 값 변경
        Optional.ofNullable(patchDto.getContent())
                .ifPresent(foundFeedbackReComment::setContent);

        // 저장
        feedbackReCommentRepository.save(foundFeedbackReComment);
    }

    // 대댓글 조회
    @Override
    public  ReCommentResponseDto.Details responseReComment(Long feedbackBoardId, Long commentId, Long reCommentId){
        // 클라이언트에서 보낸 ID값으로 Entity 조회
        FeedbackReComment foundFeedbackReComment = findVerifiedFeedbackReComment(feedbackBoardId, commentId, reCommentId);
        //entity -> dto 매핑, 리턴
        return mapper.feedbackReCommentToReCommentDetailsResponse(foundFeedbackReComment);
    }
    @Override
    public void deleteReComment(String token, Long feedbackBoardId, Long commentId, Long reCommentId){
        // 대댓글 찾기
        FeedbackReComment foundReComment = findVerifiedFeedbackReComment(feedbackBoardId, commentId, reCommentId);

        // 멤버 검증
        Member findMember = memberService.findVerifiedMember(foundReComment.getMemberId());
        memberService.verifiedAuthenticatedMember(token, findMember);

        // 대댓글 수 -1
        Long reCommentCount = foundReComment.getFeedbackComment().getReCommentCount();
        foundReComment.getFeedbackComment().setReCommentCount(reCommentCount - 1);

        // 삭제
        feedbackReCommentRepository.delete(foundReComment);
    }

    // 피드백 대댓글 찾는 메서드
    private FeedbackReComment findVerifiedFeedbackReComment(Long feedbackBoardId, Long commentId, Long reCommentId) {
        Optional<FeedbackReComment> feedbackReComment = feedbackReCommentRepository.findById(new ReCommentPK(new CommentPK(feedbackBoardId, commentId),reCommentId));
        return feedbackReComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RE_COMMENT_NOT_FOUND));
    }
}
