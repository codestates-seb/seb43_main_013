package com.CreatorConnect.server.board.comments.jobcomment.service;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.common.service.CommentService;
import com.CreatorConnect.server.board.comments.jobcomment.entity.JobComment;
import com.CreatorConnect.server.board.comments.jobcomment.mapper.JobCommentMapper;
import com.CreatorConnect.server.board.comments.jobcomment.repository.JobCommentRepository;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import com.CreatorConnect.server.board.jobboard.repository.JobBoardRepository;
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
public class JobCommentServiceImpl implements CommentService  {
    private final JobBoardRepository jobBoardRepository;
    private final JobCommentRepository jobCommentRepository;
    private final JobCommentMapper mapper;
    private final MemberService memberService;

    // 댓글 등록
    @Override
    public CommentResponseDto.Post createComment(Long id, CommentDto.Post postDto) {
        // 멤버 검증
        memberService.verifiedAuthenticatedMember(postDto.getMemberId());

        // jobBoard찾기
        Optional<JobBoard> jobBoard = jobBoardRepository.findById(id);
        JobBoard foundJob = jobBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_NOT_FOUND));

        // dto to Entity
        JobComment jobComment = mapper.dtoToJobComment(id, postDto, foundJob);

        // entity to dto
        CommentResponseDto.Post post = new CommentResponseDto.Post();
        post.setCommentId(jobComment.getCommentPK().getCommentId());

        return post;
    }

    //댓글 수정
    @Override
    public void updateComment(Long jobBoardId, Long commentId, CommentDto.Patch patchDto){

        // Dto의 Id값으로 Entity찾기
        JobComment foundjobComment = findVerifiedJobComment(jobBoardId, commentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(foundjobComment.getMemberId());

        //찾은 Entity의 값 변경
        Optional.ofNullable(patchDto.getContent())
                .ifPresent(foundjobComment::setContent);

        jobCommentRepository.save(foundjobComment);
    }

    // 댓글 조회
    @Override
    public  CommentResponseDto.Details responseComment(Long jobBoardId, Long commentId){
        // 클라이언트에서 보낸 ID값으로 Entity 조회
        JobComment foundComment = findVerifiedJobComment(jobBoardId, commentId);
        //entity -> dto 매핑, 리턴
        return mapper.jobCommentToCommentDetailsResponse(foundComment);
    }

    // 댓글 목록 조회
    @Override
    public CommentResponseDto.Multi<CommentResponseDto.Details> responseComments(Long jobBoardId, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("commentPK").ascending());
        // Page 생성 - 최신순
        Page<JobComment> jobCommentsPage = jobCommentRepository.findByJobBoardId(jobBoardId, pageRequest);

        // 코맨트 리스트 가져오기
        List<CommentResponseDto.Details> responses = mapper.jobCommentsToCommentDetailsResponses(jobCommentsPage.getContent());

        // pageInfo 가져오기
        CommentResponseDto.PageInfo pageInfo = new CommentResponseDto.PageInfo(jobCommentsPage.getNumber() + 1, jobCommentsPage.getSize(), jobCommentsPage.getTotalElements(), jobCommentsPage.getTotalPages());

        return new CommentResponseDto.Multi<>(responses, pageInfo);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long jobBoardId, Long commentId) {
        // 댓글 찾기
        JobComment foundComment = findVerifiedJobComment(jobBoardId, commentId);

        // 멤버 검증
        memberService.verifiedAuthenticatedMember(foundComment.getMemberId());

        // 댓글 수 -1
        Long commentCount = foundComment.getJobBoard().getCommentCount();
        foundComment.getJobBoard().setCommentCount(commentCount - 1);

        jobCommentRepository.delete(foundComment);
    }

    // 댓글 찾는 메서드
    private JobComment findVerifiedJobComment(Long jobBoardId, Long commentId) {
        Optional<JobComment> jobComment = jobCommentRepository.findById(new CommentPK(jobBoardId, commentId));
        return jobComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }
}

