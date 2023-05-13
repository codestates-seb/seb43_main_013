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
import com.CreatorConnect.server.feedbackcategory.service.FeedbackCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackBoardService {
    private final FeedbackBoardRepository feedbackBoardRepository;
    private final FeedbackBoardMapper mapper;
    private final CategoryRepository categoryRepository;
    private final FeedbackCategoryRepository feedbackCategoryRepository;
    private final FeedbackCategoryService feedbackCategoryService;

    //등록
    public FeedbackBoardResponseDto.Post createFeedback(FeedbackBoardDto.Post postDto){
        // Dto-Entity 변환
        FeedbackBoard feedbackBoard = mapper.feedbackBoardPostDtoToFeedbackBoard(postDto);
        Optional<Category> category = categoryRepository.findByCategoryName(postDto.getCategoryName());
        feedbackBoard.setCategory(category.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        Optional<FeedbackCategory> feedbackCategory = feedbackCategoryRepository.findByFeedbackCategoryName(postDto.getFeedbackCategoryName());
        feedbackBoard.setFeedbackCategory(feedbackCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_CATEGORY_NOT_FOUND)));

        //저장
        FeedbackBoard savedfeedbackBoard = feedbackBoardRepository.save(feedbackBoard);

        // Entity-Dto 변환 후 리턴
        FeedbackBoardResponseDto.Post responseDto = mapper.feedbackBoardToFeedbackBoardPostResponse(savedfeedbackBoard);
        return responseDto;
    }

    //수정
    public FeedbackBoardResponseDto.Patch updateFeedback(Long feedbackBoardId, FeedbackBoardDto.Patch patchDto){

        // Dto-Entity 변환
        FeedbackBoard feedbackBoard = mapper.feedbackBoardPatchDtoToFeedbackBoard(patchDto);
        feedbackBoard.setFeedbackBoardId(feedbackBoardId);

        // Dto의 Id값으로 데이터베이스에 있는 Entity 찾기
        FeedbackBoard foundFeedbackBoard = findVerifiedFeedbackBoard(feedbackBoard.getFeedbackBoardId());

        // 글 작성한 멤버가 현재 로그인한 멤버와 같은지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!Objects.equals(foundFeedbackBoard.getMember().getEmail(), authentication.getName())){
            throw new BusinessLogicException(ExceptionCode.INVALID_MEMBER);
        }

        // 찾은 Entity의 값 변경
        Optional.ofNullable(feedbackBoard.getTitle())
                .ifPresent(foundFeedbackBoard::setTitle);
        Optional.ofNullable(feedbackBoard.getLink())
                .ifPresent(foundFeedbackBoard::setLink);
        Optional.ofNullable(feedbackBoard.getContent())
                .ifPresent(foundFeedbackBoard::setContent);
        Optional<Category> category = categoryRepository.findByCategoryName(patchDto.getCategoryName());
        foundFeedbackBoard.setCategory(category.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        Optional<FeedbackCategory> feedbackCategory = feedbackCategoryRepository.findByFeedbackCategoryName(patchDto.getFeedbackCategoryName());
        foundFeedbackBoard.setFeedbackCategory(feedbackCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_CATEGORY_NOT_FOUND)));
        Optional.ofNullable(feedbackBoard.getTag())
                .ifPresent(foundFeedbackBoard::setTag);

        // 저장
        FeedbackBoard updatedFeedbackBoard = feedbackBoardRepository.save(foundFeedbackBoard);

        // Entity-Dto 변환 후 리턴
        FeedbackBoardResponseDto.Patch responseDto = mapper.feedbackBoardToFeedbackBoardPatchResponse(updatedFeedbackBoard);
        return responseDto;
    }

    // 개별 조회
    public FeedbackBoardResponseDto.Details responseFeedback(Long FeedbackBoardId){
        // 클라이언트에서 보낸 ID값으로 데이터베이스에 있는 Entity 찾아서 리턴
        FeedbackBoard foundFeedbackBoard = findVerifiedFeedbackBoard(FeedbackBoardId);
        return mapper.feedbackBoardToFeedbackBoardDetailsResponse(foundFeedbackBoard);
    }

    //목록 조회
    public FeedbackBoardResponseDto.Multi<FeedbackBoardResponseDto.Details> responseFeedbacks(int page, int size){
        //Page 생성 - 피드백 보드Id 기준 내림차순 정렬 적용
        Page<FeedbackBoard> feedbackBoardsPage = feedbackBoardRepository.findAll(PageRequest.of(page - 1, size, Sort.by("feedbackBoardId").descending()));

        // 피드백 리스트 가져오기
        List<FeedbackBoardResponseDto.Details> responses = mapper.feedbackBoardsToFeedbackBoardDetailsResponses(feedbackBoardsPage.getContent());

        // pageInfo 가져오기
        FeedbackBoardResponseDto.PageInfo pageInfo = new FeedbackBoardResponseDto.PageInfo(feedbackBoardsPage.getNumber() + 1, feedbackBoardsPage.getSize(), feedbackBoardsPage.getTotalElements(), feedbackBoardsPage.getTotalPages());

        // 리턴
        return new FeedbackBoardResponseDto.Multi<>(responses, pageInfo);
    }

    //피드백 카테고리로 목록 조회
    public FeedbackBoardResponseDto.Multi<FeedbackBoardResponseDto.Details> responseFeedbacksByCategory(Long feedbackCategoryId, int page, int size){
        //page생성 - 피드백 카테고리 ID로 검색 후 피드백 보드Id 기준 내림차순 정렬 적용
        Page<FeedbackBoard> feedbackBoardsPage = feedbackBoardRepository.findFeedbackBoardsByFeedbackCategoryId(feedbackCategoryId, PageRequest.of(page - 1, size, Sort.by("feedbackBoardId").descending()));

        // 피드백 리스트 가져오기
        List<FeedbackBoardResponseDto.Details> responses = mapper.feedbackBoardsToFeedbackBoardDetailsResponses(feedbackBoardsPage.getContent());

        // pageInfo 가져오기
        FeedbackBoardResponseDto.PageInfo pageInfo = new FeedbackBoardResponseDto.PageInfo(feedbackBoardsPage.getNumber() + 1, feedbackBoardsPage.getSize(), feedbackBoardsPage.getTotalElements(), feedbackBoardsPage.getTotalPages());

        //리턴
        return new FeedbackBoardResponseDto.Multi<>(responses, pageInfo);
    }

    //삭제
    public void deleteFeedback(Long feedbackBoardId) {
        // 피드백 ID로 피드백 찾기
        FeedbackBoard feedbackBoard = findVerifiedFeedbackBoard(feedbackBoardId);

        // 글 작성한 멤버가 현재 로그인한 멤버와 같은지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!Objects.equals(feedbackBoard.getMember().getEmail(), authentication.getName())){
            throw new BusinessLogicException(ExceptionCode.INVALID_MEMBER);
        }

        // 삭제
        feedbackBoardRepository.delete(feedbackBoard);
    }


    //피드백 아이디로 피드백 찾는 메서드
    public FeedbackBoard findVerifiedFeedbackBoard(Long feedbackBoardId) {
        Optional<FeedbackBoard> FeedbackBoard = feedbackBoardRepository.findById(feedbackBoardId);
        return FeedbackBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_NOT_FOUND));
    }

}
