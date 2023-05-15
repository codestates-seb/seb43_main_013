package com.CreatorConnect.server.comment.service;

import com.CreatorConnect.server.comment.dto.CommentDto;
import com.CreatorConnect.server.comment.dto.CommentResponseDto;
import com.CreatorConnect.server.comment.entity.CommentPK;
import com.CreatorConnect.server.comment.entity.FreeComment;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.freeboard.repository.FreeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeCommentService {
    private final FreeBoardRepository freeBoardRepository;

    public CommentResponseDto.Post createComment(long id, CommentDto.Post postDto) {
        FreeComment freeComment = dtoToFreeComment(id,postDto);

        // entity to dto
        CommentResponseDto.Post post = new CommentResponseDto.Post();
        post.setCommentId(freeComment.getCommentPK().getCommentId());
        post.setContent( freeComment.getContent());
        return post;
    }

    public FreeComment dtoToFreeComment(long id, CommentDto.Post postDto ){
        //feedbackBoard찾기
        Optional<FreeBoard> freeBoard = freeBoardRepository.findById(id);
        FreeBoard foundFreePost = freeBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FREEBOARD_NOT_FOUND));

        // dto-entity 수동 매핑
        FreeComment freeComment = new FreeComment();
        CommentPK commentPK = new CommentPK(id, foundFreePost.getCommentCount()+1);
        freeComment.setCommentPK(commentPK);
        freeComment.setContent(postDto.getContent());
        freeComment.setMember(postDto.getMember());
        freeComment.setFreeBoard(foundFreePost);

        // board commentCount +1
        foundFreePost.setCommentCount(foundFreePost.getCommentCount()+1);
        return freeComment;
    }
}
