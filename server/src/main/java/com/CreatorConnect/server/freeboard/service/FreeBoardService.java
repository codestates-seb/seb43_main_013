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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
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
     * 1. 게시글 존재 여부 확인
     * 2. 카테고리 유효성 검증 (변경한 카테고리가 존재하는 카테고리?)
     * 3. 수정
     * 4. 수정된 데이터 저장
     */
    public FreeBoard updateFreeBoard(FreeBoardDto.Patch patch, long freeboardId) {
        // 1. 게시글 존재 여부 확인
        patch.setFreeBoardId(freeboardId);
        FreeBoard freeBoard = mapper.freeBoardPatchDtoToFreeBoard(patch);
        FreeBoard checkedFreeBoard = verifyFreeBoard(freeBoard.getFreeBoardId());


        // 2. 카테고리를 수정할 경우 카테고리 유효성 검증 (변경한 카테고리가 존재하는 카테고리?)
        if (patch.getCategoryName() != null) { // 카테고리 변경이 된 경우
            categoryService.verifyCategory(patch.getCategoryName()); // 수정된 카테고리 존재 여부 확인
            // 카테고리 수정
            Optional<Category> category = categoryRepository.findByCategoryName(patch.getCategoryName());
            checkedFreeBoard.setCategory(category.orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        }


        // 3. 수정
        Optional.ofNullable(freeBoard.getTitle())
                .ifPresent(title -> checkedFreeBoard.setTitle(title)); // 게시글 제목 수정

        Optional.ofNullable(freeBoard.getContent())
                .ifPresent(content -> checkedFreeBoard.setContent(content)); // 게시글 내용 수정


        log.info("categoryName : {}",checkedFreeBoard.getCategoryName());

        // 태그 수정 추가 예정

        // 4. 수정된 데이터 저장
        return freeBoardRepository.save(checkedFreeBoard);

    }

    /**
     * <자유 게시판 게시글 목록>
     */
    public Page<FreeBoard> getFreeBoards(String sort, int page, int size) {
        return  freeBoardRepository.findAll(sortedPage(sort, page, size));
    }

    /**
     * <자유 게시판 카테고리 별 목록>
     */
    public Page<FreeBoard> getFreeBoardsByCategory(long categoryId, String sort, int page, int size) {
        return freeBoardRepository.findFreeBoardsByCategoryId(categoryId, sortedPage(sort, page, size));
//        Page<FreeBoard> freeBoards = freeBoardRepository.findAll(pageRequest);
//        List<FreeBoardDto.Response> responses = findFreeBoardByCategoryId(categoryId);
//
//        return new FreeBoardDto.MultiResponseDto<>(responses, freeBoards);
    }

    /**
     * <자유 게시판 게시글 상세 조회>
     * 1. 게시글 존재 여부 확인
     * 2. 조회수 증가
     */
    public FreeBoard getFreeBoardDetail(long freeboardId) {
        // 1. 게시글 존재 여부 확인
        FreeBoard freeBoard = verifyFreeBoard(freeboardId);

        // 2. 조회수 증가
        addViews(freeBoard);

        return freeBoard;
    }

    /**
     * <자유 게시판 게시글 삭제>
     * 1. 게시글 존재 여부 확인
     * 2. 삭제
     */
    public void removeFreeBoard(long freeboardId) {
        // 1. 게시글 존재 여부 획인
        FreeBoard freeBoard = verifyFreeBoard(freeboardId);

        // 2. 삭제
        freeBoardRepository.delete(freeBoard);
    }

    // 게시글이 존재 여부 검증 메서드
    private FreeBoard verifyFreeBoard(long freeboardId) {
        Optional<FreeBoard> optionalFreeBoard = freeBoardRepository.findById(freeboardId);
        return optionalFreeBoard.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.FREEBOARD_NOT_FOUND));
    }

//    public List<FreeBoardDto.Response> findFreeBoardByCategoryId(long categoryId) {
//       Category category = categoryService.verifyCategory(categoryId);
//        return mapper.freeBoardToFreeBoardResponseDtos(freeBoardRepository.findByCategory(category));
//    }

    // 조회수 증가 메서드
    private void addViews(FreeBoard freeBoard) {
        freeBoard.setViewCount(freeBoard.getViewCount() + 1);
        freeBoardRepository.save(freeBoard);
    }

    //페이지 정렬 메서드
    private PageRequest sortedPage(String sort, int page, int size) {
        if(Objects.equals(sort,"최신순")){
            return PageRequest.of(page, size, Sort.by("freeBoardId").descending());
        } else if(Objects.equals(sort,"등록순")){
            return PageRequest.of(page, size, Sort.by("freeBoardId").ascending());
        } else if(Objects.equals(sort,"인기순")){
            return PageRequest.of(page, size, Sort.by("viewCount","freeBoardId").descending());
        } else {
            return PageRequest.of(page, size, Sort.by("freeBoardId").descending());
        }
    }

}
