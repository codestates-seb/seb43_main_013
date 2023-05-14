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
import com.CreatorConnect.server.tag.dto.TagDto;
import com.CreatorConnect.server.tag.entity.Tag;
import com.CreatorConnect.server.tag.mapper.TagMapper;
import com.CreatorConnect.server.tag.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final TagService tagService;
    private final TagMapper tagMapper;

    public FreeBoardService(FreeBoardRepository freeBoardRepository,
                            MemberService memberService,
                            CategoryRepository categoryRepository,
                            FreeBoardMapper mapper,
                            MemberRepository memberRepository,
                            CategoryService categoryService,
                            TagService tagService,
                            TagMapper tagMapper) {
        this.freeBoardRepository = freeBoardRepository;
        this.memberService = memberService;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.memberRepository = memberRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    /**
     * <자유 게시판 게시글 등록>
     * 1. 회원 매핑
     * 2. 카테고리 매핑
     * 3. 게시글 등록
     * 4. 태그 저장
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
        FreeBoard createdFreeBoard =  freeBoardRepository.save(freeBoard);

        // 4. 태그 저장
        List<Tag> tags = tagMapper.tagPostDtosToTag(post.getTags());
        List<Tag> createTags = tagService.createFreeBoardTag(tags, createdFreeBoard);

        return createdFreeBoard;
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

//        List<Tag> tags = tagMapper.tagPostDtosToTag(patch.getTags());
//        tagService.updateFreeBoardTag(tags, checkedFreeBoard);

        log.info("categoryName : {}",checkedFreeBoard.getCategoryName());

        // 4. 수정된 데이터 저장
        return freeBoardRepository.save(checkedFreeBoard);

    }

    /**
     * <자유 게시판 게시글 목록>
     */
    public Page<FreeBoard> getFreeBoards(int page, int size) {
        return freeBoardRepository.findAll(PageRequest.of(page, size, Sort.by("freeBoardId").descending()));
    }

    /**
     * <자유 게시판 카테고리 별 목록>
     */
    public Page<FreeBoard> getFreeBoardsByCategory(long categoryId, int page, int size) {
        return freeBoardRepository.findFreeBoardsByCategoryId(categoryId, PageRequest.of(page, size, Sort.by("freeBoardId").descending()));
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


}
