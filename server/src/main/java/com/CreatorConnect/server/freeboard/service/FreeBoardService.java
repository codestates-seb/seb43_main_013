package com.CreatorConnect.server.freeboard.service;

import com.CreatorConnect.server.category.service.CategoryService;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.freeboard.repository.FreeBoardRepository;
import com.CreatorConnect.server.member.service.MemberService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;

    public FreeBoardService(FreeBoardRepository freeBoardRepository,
                            MemberService memberService,
                            CategoryService categoryService) {
        this.freeBoardRepository = freeBoardRepository;
        this.memberService = memberService;
        this.categoryService = categoryService;
    }

    /**
     * <자유 게시판 게시글 등록>
     * 1. 회원 검증 (존재하는 회원?)
     * 2. 카테고리 검증 (존재하는 카테고리?)
     * 3. 게시글 등록
     */
    public FreeBoard createFreeBoard(FreeBoard freeBoard) {
        // 1. 회원 검증 (존재하는 회원?)
        Long memberId = freeBoard.getMember().getMemberId();
        memberService.findVerifiedMember(memberId);

        // 2. 카테고리 검증 (존재하는 카테고리?)
        String categoryName = freeBoard.getCategory().getCategory();
        Long categoryId = categoryService.findCategoryId(categoryName);


        // 3. 게시글 등록
        return freeBoardRepository.save(freeBoard);
    }
}
