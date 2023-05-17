package com.CreatorConnect.server.board.comments.common.service;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;

public interface CommentService {
    CommentResponseDto.Post createComment(Long id, CommentDto.Post postDto);
    void updateComment(String token,Long boardId, Long commentId, CommentDto.Patch patchDto);
    CommentResponseDto.Details responseComment(Long boardId, Long commentId);
    CommentResponseDto.Multi<CommentResponseDto.Details> responseComments(Long boardId, int page, int size);
    void deleteComment(String token, Long boardId, Long commentId);
}
