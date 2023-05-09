package com.CreatorConnect.server.freeboard.service;

import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.category.repository.CategoryRepository;
import com.CreatorConnect.server.category.service.CategoryService;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.freeboard.mapper.FreeBoardMapper;
import com.CreatorConnect.server.freeboard.repository.FreeBoardRepository;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final FreeBoardMapper mapper;
    private final MemberRepository memberRepository;
    private final CategoryService categoryService;

    public FreeBoardService(FreeBoardRepository freeBoardRepository,
                            MemberService memberService,
                            CategoryRepository categoryRepository,
                            FreeBoardMapper mapper,
                            MemberRepository memberRepository,
                            CategoryService categoryService) {
        this.freeBoardRepository = freeBoardRepository;
        this.memberService = memberService;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.memberRepository = memberRepository;
        this.categoryService = categoryService;
    }

    /**
     * <자유 게시판 게시글 등록>
     * 1. 회원 매핑
     * 2. 카테고리 매핑
     * 3. 게시글 등록
     */
    public FreeBoard createFreeBoard(FreeBoardDto.Post post) {
        FreeBoard freeBoard = mapper.freeBoardPostDtoToFreeBoard(post);

        // 회원 매핑
        Optional<Member> member = memberRepository.findById(post.getMemberId());
        freeBoard.setMember(member.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)));

        // 카테고리 매핑
        Optional<Category> category = categoryRepository.findByCategoryName(post.getCategoryName());
        freeBoard.setCategory(category.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.CATEGORY_EXISTS)));

        // 3. 게시글 등록
        return freeBoardRepository.save(freeBoard);
    }

    /**
     * <자유 게시판 게시글 수정>
     * 1. FreeBoardDto.Patch에 freeboardId 설정 및 FreeBoard 객체로 변환
     * 2. 게시글이 존재하는 지 확인
     * 3. 수정
     * 4. 수정된 값 저장
     */
    public FreeBoard updateFreeBoard(FreeBoardDto.Patch patch, long freeboardId) {
        // 1. FreeBoardDto.Patch에 freeboardId 설정 및 FreeBoard 객체로 변환
        patch.setFreeBoardId(freeboardId);
        FreeBoard freeBoard = mapper.freeBoardPatchDtoToFreeBoard(patch);

        // 2. 게시글이 존재하는 지 확인
        FreeBoard checkedFreeBoard = verifyFreeBoard(freeboardId);

//        // 회원 매핑
//        Optional<Member> member = memberRepository.findById(patch.getMemberId());
//        freeBoard.setMember(member.orElseThrow(() ->
//                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)));
//

        // 3. 수정
        Optional.ofNullable(freeBoard.getTitle())
                .ifPresent(title -> checkedFreeBoard.setTitle(title)); // 게시글 제목 수정

        Optional.ofNullable(freeBoard.getContent())
                .ifPresent(content -> checkedFreeBoard.setContent(content)); // 게시글 내용 수정

        Optional.ofNullable(freeBoard.getCategory())
                .ifPresent(category -> checkedFreeBoard.setCategory(category)); // 카테고리 수정


        // 카테고리 매핑
//        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(checkedFreeBoard.getCategoryName());
//        checkedFreeBoard.setCategory(optionalCategory.orElseThrow(() ->
//                new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));

        log.info("categoryName : {}",checkedFreeBoard.getCategoryName());

        // 태그 수정 추가 예정

        // 4. 수정된 값 저장
        return freeBoardRepository.save(checkedFreeBoard);

    }

    /**
     * <자유 게시판 게시글 목록>
     */
    public Page<FreeBoard> getFreeBoards(int page, int size) {
        return freeBoardRepository.findAll(PageRequest.of(page, size, Sort.by("freeboardId").descending()));
    }

    /**
     * <자유 게시판 카테고리 별 목록>
     */
    public Page<FreeBoard> getFreeBoardsByCategory(long categoryId, int page, int size) {
        return freeBoardRepository.findFreeBoardsByCategoryId(categoryId, PageRequest.of(page, size, Sort.by("freeboardId").descending()));
//        Page<FreeBoard> freeBoards = freeBoardRepository.findAll(pageRequest);
//        List<FreeBoardDto.Response> responses = findFreeBoardByCategoryId(categoryId);
//
//        return new FreeBoardDto.MultiResponseDto<>(responses, freeBoards);
    }

    // 게시글이 존재 여부 검증 메서드
    private FreeBoard verifyFreeBoard(long freeboardId) {
        Optional<FreeBoard> optionalFreeBoard = freeBoardRepository.findById(freeboardId);
        return optionalFreeBoard.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.FREEBOARD_NOT_FOUND));
    }

    public List<FreeBoardDto.Response> findFreeBoardByCategoryId(long categoryId) {
       Category category = categoryService.verifyCategory(categoryId);
        return mapper.freeBoardToFreeBoardResponseDtos(freeBoardRepository.findByCategory(category));
    }


}
