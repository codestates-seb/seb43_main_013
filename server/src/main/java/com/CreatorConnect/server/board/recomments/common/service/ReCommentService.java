package com.CreatorConnect.server.board.recomments.common.service;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;

public interface ReCommentService {
    ReCommentResponseDto.Post createReComment(Long boardId, Long commentId, ReCommentDto.Post postdto);
    void updateReComment(Long boardId, Long commentId, Long reCommentId, CommentDto.Patch patchDto);
    ReCommentResponseDto.Details responseReComment(Long boardId, Long commentId, Long reCommentId);
    void deleteReComment(Long boardId, Long commentId, Long reCommentId);
}
