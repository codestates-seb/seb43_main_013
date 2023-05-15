package com.CreatorConnect.server.comment.service;

import com.CreatorConnect.server.comment.dto.CommentDto;
import com.CreatorConnect.server.comment.dto.CommentResponseDto;
import com.CreatorConnect.server.comment.entity.CommentPK;
import com.CreatorConnect.server.comment.entity.FeedbackComment;
import com.CreatorConnect.server.comment.entity.FreeComment;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.feedbackboard.repository.FeedbackBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackCommentService {
    private final FeedbackBoardRepository feedbackBoardRepository;

    public CommentResponseDto.Post createComment( long id, CommentDto.Post postDto) {
        FeedbackComment feedbackComment = dtoToFeedbackComment(id,postDto);
        // entity to dto
        CommentResponseDto.Post post = new CommentResponseDto.Post();
        post.setCommentId(feedbackComment.getCommentPK().getCommentId());
        post.setContent( feedbackComment.getContent());
        return post;
    }

    public FeedbackComment dtoToFeedbackComment(long id, CommentDto.Post postDto ){
        //feedbackBoard찾기
        Optional<FeedbackBoard> feedbackBoard = feedbackBoardRepository.findById(id);
        FeedbackBoard foundFeedback = feedbackBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_NOT_FOUND));

        // dto-entity 수동 매핑
        FeedbackComment feedbackComment = new FeedbackComment();
        CommentPK commentPK = new CommentPK(id, foundFeedback.getCommentCount()+1);
        feedbackComment.setCommentPK(commentPK);
        feedbackComment.setContent(postDto.getContent());
        feedbackComment.setMember(postDto.getMember());
        feedbackComment.setFeedbackBoard(foundFeedback);

        // board commentCount +1
        foundFeedback.setCommentCount(foundFeedback.getCommentCount()+1);
        return feedbackComment;
    }
}
