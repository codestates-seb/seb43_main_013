package com.CreatorConnect.server.feedbackboard.service;

import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.category.repository.CategoryRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.feedbackboard.mapper.FeedbackBoardMapper;
import com.CreatorConnect.server.feedbackboard.repository.FeedbackBoardRepository;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.feedbackcategory.repository.FeedbackCategoryRepository;
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
public class FeedbackBoardService {
    private final FeedbackBoardRepository feedbackBoardRepository;
    private final FeedbackBoardMapper mapper;
    private final CategoryRepository categoryRepository;
    private final FeedbackCategoryRepository feedbackCategoryRepository;

    public FeedbackBoardResponseDto.Post createFeedback(FeedbackBoardDto.Post postDto){
        //매핑
        FeedbackBoard feedbackBoard = mapper.feedbackBoardPostDtoToFeedbackBoard(postDto);
        Optional<Category> category = categoryRepository.findByCategoryName(postDto.getCategoryName());
        feedbackBoard.setCategory(category.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        Optional<FeedbackCategory> feedbackCategory = feedbackCategoryRepository.findByFeedbackCategoryName(postDto.getFeedbackCategoryName());
        feedbackBoard.setFeedbackCategory(feedbackCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_CATEGORY_NOT_FOUND)));

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

    public FeedbackBoardResponseDto.Details responseFeedback(Long FeedbackBoardId){
        FeedbackBoard foundFeedbackBoard = findVerifiedFeedbackBoard(FeedbackBoardId);
        return mapper.feedbackBoardToFeedbackBoardDetailsResponse(foundFeedbackBoard);
    }
    public FeedbackBoardResponseDto.Multi<FeedbackBoardResponseDto.Details> responseFeedbacks(int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("feedbackBoardId").descending());
        Page<FeedbackBoard> feedbackBoardsPage = feedbackBoardRepository.findAll(pageRequest);
        List<FeedbackBoardResponseDto.Details> responses = mapper.feedbackBoardsToFeedbackBoardDetailsResponses(feedbackBoardsPage.getContent());
        FeedbackBoardResponseDto.PageInfo pageInfo = new FeedbackBoardResponseDto.PageInfo(feedbackBoardsPage.getNumber() + 1, feedbackBoardsPage.getSize(), feedbackBoardsPage.getTotalElements(), feedbackBoardsPage.getTotalPages());
        return new FeedbackBoardResponseDto.Multi<>(responses, pageInfo);
    }

//    public FeedbackBoardResponseDto.Multi<FeedbackBoardResponseDto.Details> responseFeedbacksByCategory(Long feedbackCategoryId, int page, int size){
//        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("questionId").descending());
//        Page<FeedbackBoard> feedbackBoardsPage = feedbackBoardRepository.findAll(pageRequest);
//        // Todo 피드백 카테고리 아이디로 피드백 목록 찾는 메서드(2)
//        List<FeedbackBoardResponseDto.Details> responses = findFeedbacksByCategoryId(feedbackCategoryId);
//       FeedbackBoardResponseDto.PageInfo pageInfo = new FeedbackBoardResponseDto.PageInfo(feedbackBoardsPage.getNumber() + 1, feedbackBoardsPage.getSize(), feedbackBoardsPage.getTotalElements(), feedbackBoardsPage.getTotalPages());
//        return new FeedbackBoardResponseDto.Multi<>(responses, pageInfo);
//    }

    public void deleteFeedback(Long feedbackBoardId) {
        FeedbackBoard feedbackBoard = findVerifiedFeedbackBoard(feedbackBoardId);
        feedbackBoardRepository.delete(feedbackBoard);
    }


    //피드백 아이디로 피드백 찾는 메서드
    public FeedbackBoard findVerifiedFeedbackBoard(Long feedbackBoardId) {
        Optional<FeedbackBoard> FeedbackBoard = feedbackBoardRepository.findById(feedbackBoardId);
        return FeedbackBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_NOT_FOUND));
    }

    // Todo 피드백 카테고리 아이디로 피드백 목록 찾는 메서드
//    public List<FeedbackBoardMultiDto.Response> findFeedbacksByCategoryId(Long feedbackCategoryId) {
//        FeedbackCategory feedbackCategory = FeedbackCategoryService.findVerifiedCategory(feedbackCategoryId);
//        mapper.feedbackBoardsToFeedbackBoardDetailsResponses(FeedbackBoardRepository.findByCategory(feedbackCategory));
//    }
}
