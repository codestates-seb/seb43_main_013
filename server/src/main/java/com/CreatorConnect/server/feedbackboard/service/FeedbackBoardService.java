package com.CreatorConnect.server.feedbackboard.service;

import com.CreatorConnect.server.auth.userdetails.MemberDetailsService;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.feedbackboard.mapper.FeedbackBoardMapper;
import com.CreatorConnect.server.feedbackboard.repository.FeedbackBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackBoardService {
    private final FeedbackBoardRepository feedbackBoardRepository;
    private final FeedbackBoardMapper mapper;

    public FeedbackBoardDto.PostResponse createFeedback(FeedbackBoardDto.Post postDto){
        FeedbackBoard feedbackBoard = mapper.feedbackBoardPostDtoToFeedbackBoard(postDto);
        FeedbackBoard savedfeedbackBoard = feedbackBoardRepository.save(feedbackBoard);
        FeedbackBoardDto.PostResponse responseDto = mapper.feedbackBoardToFeedbackBoardPostResponse(savedfeedbackBoard);
        responseDto.setMassage("게시글이 등록되었습니다.");
        return responseDto;
    }

    public FeedbackBoardDto.PatchResponse updateFeedback(Long feedbackBoardId, FeedbackBoardDto.Patch patchDto){

    }
}
