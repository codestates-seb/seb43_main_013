package com.CreatorConnect.server.board.promotionboard.service;

import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.categories.category.repository.CategoryRepository;
import com.CreatorConnect.server.board.categories.category.service.CategoryService;
import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardDto;
import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardResponseDto;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import com.CreatorConnect.server.board.promotionboard.mapper.PromotionBoardMapper;
import com.CreatorConnect.server.board.promotionboard.repository.PromotionBoardRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionBoardService {
    private final PromotionBoardMapper mapper;
    private final PromotionBoardRepository promotionBoardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    // 등록
    public PromotionBoardResponseDto.Post createPromotion(PromotionBoardDto.Post postDto){

        memberService.verifiedAuthenticatedMember(postDto.getMemberId());

        PromotionBoard promotionBoard = mapper.promotionBoardPostDtoToPromotionBoard(postDto);
        Optional<Category> category = categoryRepository.findByCategoryName(postDto.getCategoryName());
        promotionBoard.setCategory(category.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));

        PromotionBoard savePromotionBoard = promotionBoardRepository.save(promotionBoard);

        PromotionBoardResponseDto.Post responseDto = mapper.promotionBoardToPromotionPostResponse(savePromotionBoard);
        return responseDto;

    }
}
