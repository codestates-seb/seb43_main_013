package com.CreatorConnect.server.board.recomments.jobrecomment.service;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.jobcomment.entity.JobComment;
import com.CreatorConnect.server.board.comments.jobcomment.repository.JobCommentRepository;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.common.service.ReCommentService;
import com.CreatorConnect.server.board.recomments.jobrecomment.entity.JobReComment;
import com.CreatorConnect.server.board.recomments.jobrecomment.mapper.JobReCommentMapper;
import com.CreatorConnect.server.board.recomments.jobrecomment.repository.JobReCommentRepository;
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
public class JobReCommentServiceImpl implements ReCommentService {
    private final JobCommentRepository jobCommentRepository;
    private final JobReCommentRepository jobReCommentRepository;
    private final JobReCommentMapper mapper;
    private final MemberService memberService;

    @Override
    public ReCommentResponseDto.Post createReComment(Long jobBoardId, Long commentId, ReCommentDto.Post postdto) {
        // 멤버 검증
        memberService.verifiedAuthenticatedMember(postdto.getMemberId());

        // 댓글 찾기
        Optional<JobComment> jobComment = jobCommentRepository.findById(new CommentPK(jobBoardId,commentId));
        JobComment foundJobComment = jobComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        // dto to Entity
        JobReComment jobReComment = mapper.dtoToJobReComment(postdto, foundJobComment);

        // Entity to dto
        ReCommentResponseDto.Post post = new ReCommentResponseDto.Post();
        post.setRecommentId(jobReComment.getReCommentPK().getReCommentId());

        // response
        return  post;
    }
    @Override
    public void updateReComment(Long jobBoardId, Long commentId, Long reCommentId, CommentDto.Patch patchDto){

        // Dto의 Id값으로 Entity찾기
        JobReComment foundJobReComment = findVerifiedJobReComment(jobBoardId, commentId, reCommentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(patchDto.getMemberId());

        //찾은 Entity의 값 변경
        Optional.ofNullable(patchDto.getContent())
                .ifPresent(foundJobReComment::setContent);

        // 저장
        jobReCommentRepository.save(foundJobReComment);
    }

    // 대댓글 조회
    @Override
    public  ReCommentResponseDto.Details responseReComment(Long jobBoardId, Long commentId, Long reCommentId){
        // 클라이언트에서 보낸 ID값으로 Entity 조회
        JobReComment foundJobReComment = findVerifiedJobReComment(jobBoardId, commentId, reCommentId);
        // entity -> dto 매핑, 리턴
        return mapper.jobReCommentToReCommentDetailsResponse(foundJobReComment);
    }
    @Override
    public void deleteReComment(Long jobBoardId, Long commentId, Long reCommentId){
        // 대댓글 찾기
        JobReComment foundReComment = findVerifiedJobReComment(jobBoardId, commentId, reCommentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(foundReComment.getMemberId());

        // 대댓글 수 -1
        Long reCommentCount = foundReComment.getJobComment().getReCommentCount();
        foundReComment.getJobComment().setReCommentCount(reCommentCount - 1);

        // 삭제
        jobReCommentRepository.delete(foundReComment);
    }

    // 대댓글 찾는 메서드
    private JobReComment findVerifiedJobReComment(Long jobBoardId, Long commentId, Long reCommentId) {
        Optional<JobReComment> jobReComment = jobReCommentRepository.findById(new ReCommentPK(new CommentPK(jobBoardId, commentId),reCommentId));
        return jobReComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RE_COMMENT_NOT_FOUND));
    }
}