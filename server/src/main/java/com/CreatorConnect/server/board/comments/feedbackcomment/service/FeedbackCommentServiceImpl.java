package com.CreatorConnect.server.board.comments.feedbackcomment.service;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.common.service.CommentService;
import com.CreatorConnect.server.board.comments.feedbackcomment.entity.FeedbackComment;
import com.CreatorConnect.server.board.comments.feedbackcomment.mapper.FeedbackCommentMapper;
import com.CreatorConnect.server.board.comments.feedbackcomment.repository.FeedbackCommentRepository;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.feedbackboard.repository.FeedbackBoardRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.entity.Member;
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
public class FeedbackCommentServiceImpl implements CommentService {
    private final FeedbackBoardRepository feedbackBoardRepository;
    private final FeedbackCommentRepository feedbackCommentRepository;
    private final FeedbackCommentMapper mapper;
    private final MemberService memberService;

    // 댓글 등록
    @Override
    public CommentResponseDto.Post createComment(Long feedbackBoardId, CommentDto.Post postDto) {
        // 멤버 검증
        memberService.verifiedAuthenticatedMember(postDto.getMemberId());

        //feedbackBoard찾기
        Optional<FeedbackBoard> feedbackBoard = feedbackBoardRepository.findById(feedbackBoardId);
        FeedbackBoard foundFeedback = feedbackBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_NOT_FOUND));

        //dto to Entity
        FeedbackComment feedbackComment = mapper.dtoToFeedbackComment(feedbackBoardId, postDto, foundFeedback);

        // entity to dto
        CommentResponseDto.Post post = new CommentResponseDto.Post();
        post.setCommentId(feedbackComment.getCommentPK().getCommentId());
        return post;
    }

    //댓글 수정
    @Override
    public void updateComment(Long feedbackBoardId, Long commentId, CommentDto.Patch patchDto){

        // Dto의 Id값으로 Entity찾기
        FeedbackComment foundfeedbackComment = findVerifiedFeedbackComment(feedbackBoardId, commentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(foundfeedbackComment.getMemberId());

        //찾은 Entity의 값 변경
        Optional.ofNullable(patchDto.getContent())
                .ifPresent(foundfeedbackComment::setContent);

        // 저장
        feedbackCommentRepository.save(foundfeedbackComment);

    }

    // 댓글 조회
    @Override
    public  CommentResponseDto.Details responseComment(Long feedbackBoardId, Long commentId){
        // 클라이언트에서 보낸 ID값으로 Entity 조회
        FeedbackComment foundComment = findVerifiedFeedbackComment(feedbackBoardId, commentId);
        //entity -> dto 매핑, 리턴
        return mapper.feedbackCommentToCommentDetailsResponse(foundComment);
    }

    // 댓글 목록 조회
    @Override
    public CommentResponseDto.Multi<CommentResponseDto.Details> responseComments(Long feedbackBoardId, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("commentPK").ascending());
        // Page 생성 - 최신순
        Page<FeedbackComment> feedbackCommentsPage = feedbackCommentRepository.findByFeedbackBoardId(feedbackBoardId, pageRequest);

        // 코맨트 리스트 가져오기
        List<CommentResponseDto.Details> responses = mapper.feedbackCommentsToCommentDetailsResponses(feedbackCommentsPage.getContent());

        // pageInfo 가져오기
        CommentResponseDto.PageInfo pageInfo = new CommentResponseDto.PageInfo(feedbackCommentsPage.getNumber() + 1, feedbackCommentsPage.getSize(), feedbackCommentsPage.getTotalElements(), feedbackCommentsPage.getTotalPages());

        // 리턴
        return new CommentResponseDto.Multi<>(responses, pageInfo);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long feedbackBoardId, Long commentId) {
        // 댓글 찾기
        FeedbackComment foundComment = findVerifiedFeedbackComment(feedbackBoardId, commentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(foundComment.getMemberId());

        // 댓글 수 -1
        Long commentCount = foundComment.getFeedbackBoard().getCommentCount();
        foundComment.getFeedbackBoard().setCommentCount(commentCount - 1);

        // 삭제
        feedbackCommentRepository.delete(foundComment);
    }

    // 피드백 댓글 찾는 메서드
    private FeedbackComment findVerifiedFeedbackComment(Long feedbackBoardId, Long commentId) {
        Optional<FeedbackComment> feedbackComment = feedbackCommentRepository.findById(new CommentPK(feedbackBoardId, commentId));
        return feedbackComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }
}
