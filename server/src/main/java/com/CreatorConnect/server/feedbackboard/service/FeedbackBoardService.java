package com.CreatorConnect.server.feedbackboard.service;

import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.feedbackboard.mapper.FeedbackBoardMapper;
import com.CreatorConnect.server.feedbackboard.repository.FeedbackBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackBoardService {
    private final FeedbackBoardRepository feedbackBoardRepository;
    private final FeedbackBoardMapper mapper;

    public FeedbackBoardResponseDto.Post createFeedback(FeedbackBoardDto.Post postDto){
        FeedbackBoard feedbackBoard = mapper.feedbackBoardPostDtoToFeedbackBoard(postDto);
        FeedbackBoard savedfeedbackBoard = feedbackBoardRepository.save(feedbackBoard);
        FeedbackBoardResponseDto.Post responseDto = mapper.feedbackBoardToFeedbackBoardPostResponse(savedfeedbackBoard);
        responseDto.setMassage("게시글이 등록되었습니다.");
        return responseDto;
    }

    public FeedbackBoardResponseDto.Patch updateFeedback(Long feedbackBoardId, FeedbackBoardDto.Patch patchDto){
        patchDto.setFeedbackBoardId(feedbackBoardId);
        FeedbackBoard feedbackBoard = mapper.feedbackBoardPatchDtoToFeedbackBoard(patchDto);
        FeedbackBoard foundFeedbackBoard = findVerifiedFeedbackBoard(feedbackBoard.getFeedbackBoardId());

        Optional.ofNullable(feedbackBoard.getTitle())
                .ifPresent(foundFeedbackBoard::setTitle);
        Optional.ofNullable(feedbackBoard.getLink())
                .ifPresent(foundFeedbackBoard::setLink);
        Optional.ofNullable(feedbackBoard.getContent())
                .ifPresent(foundFeedbackBoard::setContent);
        Optional.ofNullable(feedbackBoard.getCategory())
                .ifPresent(foundFeedbackBoard::setCategory);
        Optional.ofNullable(feedbackBoard.getFeedbackCategory())
                .ifPresent(foundFeedbackBoard::setFeedbackCategory);
        // Todo FeedbackBoard-tag연결(2)
//        Optional.ofNullable(feedbackBoard.getTag())
//                .ifPresent(foundFeedbackBoard::setTag);

        FeedbackBoard updatedFeedbackBoard = feedbackBoardRepository.save(foundFeedbackBoard);
        FeedbackBoardResponseDto.Patch responseDto = mapper.feedbackBoardToFeedbackBoardPatchResponse(updatedFeedbackBoard);
        responseDto.setMassage("게시글이 수정되었습니다.");
        return responseDto;
    }

    //피드백 아이디로 피드백 찾는 메서드
    public FeedbackBoard findVerifiedFeedbackBoard(Long feedbackBoardId) {
        Optional<FeedbackBoard> FeedbackBoard = feedbackBoardRepository.findById(feedbackBoardId);
        return FeedbackBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_NOT_FOUND));
    }
}
