package com.CreatorConnect.server.board.comments.recomment.service;

import com.CreatorConnect.server.board.comments.comment.entity.CommentPK;
import com.CreatorConnect.server.board.comments.comment.entity.FeedbackComment;
import com.CreatorConnect.server.board.comments.comment.repository.FeedbackCommentRepository;
import com.CreatorConnect.server.board.comments.recomment.dto.ReCommentDto;
import com.CreatorConnect.server.board.comments.recomment.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.comments.recomment.entity.FeedbackReComment;
import com.CreatorConnect.server.board.comments.recomment.mapper.ReCommentMapper;
import com.CreatorConnect.server.board.comments.recomment.repository.FeedbackReCommentRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackReCommentServiceImpl {
    private final FeedbackCommentRepository feedbackCommentRepository;
    private final FeedbackReCommentRepository feedbackReCommentRepository;
    private final ReCommentMapper mapper;

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
}
