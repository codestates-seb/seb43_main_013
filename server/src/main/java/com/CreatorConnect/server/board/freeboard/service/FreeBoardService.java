package com.CreatorConnect.server.board.freeboard.service;

import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.categories.category.repository.CategoryRepository;
import com.CreatorConnect.server.board.categories.category.service.CategoryService;
import com.CreatorConnect.server.board.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.freeboard.mapper.FreeBoardMapper;
import com.CreatorConnect.server.board.freeboard.repository.FreeBoardRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.board.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.bookmark.repository.BookmarkRepository;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.entity.Like;
import com.CreatorConnect.server.member.like.repository.LikeRepository;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import com.CreatorConnect.server.board.tag.dto.TagDto;
import com.CreatorConnect.server.board.tag.entity.Tag;
import com.CreatorConnect.server.board.tag.mapper.TagMapper;
import com.CreatorConnect.server.board.tag.service.FreeBoardTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class FreeBoardService {
    private final FreeBoardMapper mapper;
    private final FreeBoardTagService freeBoardTagService;
    private final FreeBoardRepository freeBoardRepository;
    private final TagMapper tagMapper;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;

    public FreeBoardService(FreeBoardMapper mapper, FreeBoardTagService freeBoardTagService, FreeBoardRepository freeBoardRepository, TagMapper tagMapper, CategoryService categoryService, CategoryRepository categoryRepository, MemberService memberService, MemberRepository memberRepository, LikeRepository likeRepository, BookmarkRepository bookmarkRepository) {
        this.mapper = mapper;
        this.freeBoardTagService = freeBoardTagService;
        this.freeBoardRepository = freeBoardRepository;
        this.tagMapper = tagMapper;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.likeRepository = likeRepository;
        this.bookmarkRepository = bookmarkRepository;
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

        // post dto 의 memberId 와 로그인 한 유저 비교
        memberService.verifiedAuthenticatedMember(post.getMemberId());

        // 1. 회원 매핑
        Optional<Member> member = memberRepository.findById(post.getMemberId());
        freeBoard.setMember(member.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)));

        // 2. 카테고리 매핑
        Optional<Category> category = categoryRepository.findByCategoryName(post.getCategoryName());
        freeBoard.setCategory(category.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.CATEGORY_EXISTS)));

        // 3. 게시글 등록
        FreeBoard createdFreeBoard =  freeBoardRepository.save(freeBoard);

        // 4. 태그 저장
        List<Tag> tags = tagMapper.tagPostDtosToTag(post.getTags());
        List<Tag> createTags = freeBoardTagService.createFreeBoardTag(tags, createdFreeBoard);

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

        // 2. 작성자와 로그인한 멤버 비교
        memberService.verifiedAuthenticatedMember(checkedFreeBoard.getMember().getMemberId());

        // 3. 카테고리를 수정할 경우 카테고리 유효성 검증 (변경한 카테고리가 존재하는 카테고리?)
        if (patch.getCategoryName() != null) { // 카테고리 변경이 된 경우
            categoryService.verifyCategory(patch.getCategoryName()); // 수정된 카테고리 존재 여부 확인
            // 카테고리 수정
            Optional<Category> category = categoryRepository.findByCategoryName(patch.getCategoryName());
            checkedFreeBoard.setCategory(category.orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        }

        // 4. 수정
        Optional.ofNullable(freeBoard.getTitle())
                .ifPresent(title -> checkedFreeBoard.setTitle(title)); // 게시글 제목 수정

        Optional.ofNullable(freeBoard.getContent())
                .ifPresent(content -> checkedFreeBoard.setContent(content)); // 게시글 내용 수정

        log.info("categoryName : {}",checkedFreeBoard.getCategoryName());

        // 5. 수정된 데이터 저장
        return freeBoardRepository.save(checkedFreeBoard);
    }

    /**
     * <자유 게시판 게시글 목록>
     * 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
     * 2. 로그인 여부 검증
     */
    public FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> getAllFreeBoards(int page, int size, String sort, HttpServletRequest request) {

        // 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
        Page<FreeBoard> freeBoards = freeBoardRepository.findAll(sortedBy(page, size, sort));

        // 2. 로그인 여부 검증
        String accessToken = request.getHeader("Authorization");

        Member loggedinMember = null;
        boolean bookmarked = false;
        boolean liked = false;

        List<FreeBoardDto.Response> responses = new ArrayList<>();

        if (accessToken != null){

            loggedinMember = memberService.getLoggedinMember(accessToken);

            for (FreeBoard freeBoard : freeBoards.getContent()) {
                bookmarked = loggedinMember.getBookmarks().stream()
                        .anyMatch(bookmark -> freeBoard.equals(bookmark.getFreeBoard()));

                liked = loggedinMember.getLikes().stream()
                        .anyMatch(like -> freeBoard.equals(like.getFreeBoard()));

                FreeBoardDto.Response freeBoardResponse = mapper.freeBoardToFreeBoardResponseDto(freeBoard);
                freeBoardResponse.setBookmarked(bookmarked);
                freeBoardResponse.setLiked(liked);
                responses.add(freeBoardResponse);
            }
        } else {
            responses = getResponseList(freeBoards);
        }

        return new FreeBoardDto.MultiResponseDto<>(responses, freeBoards);
    }

    /**
     * <자유 게시판 카테고리 별 목록>
     * 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
     * 2. 로그인 여부 검증
     *
     */
    public FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> getAllFreeBoardsByCategory(long categoryId, int page, int size, String sort, HttpServletRequest request) {

        // 1. 페이지네이션 적용
        Page<FreeBoard> freeBoards = freeBoardRepository.findFreeBoardsByCategoryId(categoryId, sortedBy(page, size, sort));

        // 2. 로그인 여부 검증
        String accessToken = request.getHeader("Authorization");

        Member loggedinMember = null;
        boolean bookmarked = false;
        boolean liked = false;

        List<FreeBoardDto.Response> responses = new ArrayList<>();

        if (accessToken != null){
            loggedinMember = memberService.getLoggedinMember(accessToken);

            for (FreeBoard freeBoard : freeBoards.getContent()) {
                bookmarked = loggedinMember.getBookmarks().stream()
                        .anyMatch(bookmark -> freeBoard.equals(bookmark.getFreeBoard()));

                liked = loggedinMember.getLikes().stream()
                        .anyMatch(like -> freeBoard.equals(like.getFreeBoard()));

                FreeBoardDto.Response freeBoardResponse = mapper.freeBoardToFreeBoardResponseDto(freeBoard);
                freeBoardResponse.setBookmarked(bookmarked);
                freeBoardResponse.setLiked(liked);
                responses.add(freeBoardResponse);
            }
        } else {
            responses = getResponseList(freeBoards);
        }

        return new FreeBoardDto.MultiResponseDto<>(responses, freeBoards);
    }

    // Response에 각 게시글의 태그 적용 메서드
    private List<FreeBoardDto.Response> getResponseList(Page<FreeBoard> freeBoards) {
        return freeBoards.getContent().stream().map(freeBoard -> {
            List<TagDto.TagInfo> tags = freeBoard.getTagBoards().stream()
                    .map(tagToFreeBoard -> tagMapper.tagToTagToBoard(tagToFreeBoard.getTag()))
                    .collect(Collectors.toList());
            return mapper.freeBoardToResponse(freeBoard, tags);
        }).collect(Collectors.toList());
    }

    /**
     * <자유 게시판 게시글 상세 조회>
     * 1. 게시글 존재 여부 확인
     * 2. 해당 게시글 태그 추출
     * 3. 로그인 여부 검증
     * 4. 조회수 증가
     * 5. 매핑
     * 6. 리턴
     */
    public FreeBoardDto.Response getFreeBoardDetail(long freeboardId, HttpServletRequest request) {

        // 1. 게시글 존재 여부 확인
        FreeBoard freeBoard = verifyFreeBoard(freeboardId);

        // 2. 해당 게시글 태그 추출
        List<TagDto.TagInfo> tags = freeBoard.getTagBoards().stream().map(tagToFreeBoard ->{
            TagDto.TagInfo tagInfo = tagMapper.tagToTagToBoard(tagToFreeBoard.getTag());
            return tagInfo;
        }).collect(Collectors.toList());



        // 3. 로그인 여부 검증
        String accessToken = request.getHeader("Authorization");

        Member loggedinMember = null;
        boolean bookmarked = false;
        boolean liked = false;

        // 로그인 여부 확인
        if (accessToken != null) {
            // 로그인 했을 때
            loggedinMember = memberService.getLoggedinMember(accessToken);

            if (loggedinMember != null) {
                // 게시물을 북마크한 경우
                if (loggedinMember.getBookmarks() != null) {
                    bookmarked = loggedinMember.getBookmarks().stream()
                            .anyMatch(bookmark -> freeBoard.equals(bookmark.getFreeBoard()));
                }

                // 게시물을 좋아요한 경우
                if (loggedinMember.getLikes() != null) {
                    liked = loggedinMember.getLikes().stream()
                            .anyMatch(like -> freeBoard.equals(like.getFreeBoard()));
                }
            }
        }

        // 4. 조회수 증가
        addViews(freeBoard);

        // 5. 매핑
        FreeBoardDto.Response response = mapper.freeBoardToResponse(freeBoard, tags);
        response.setBookmarked(bookmarked);
        response.setLiked(liked);

        // 6. 리턴
        return response;
    }

    /**
     * <자유 게시판 게시글 삭제>
     * 1. 게시글 존재 여부 확인
     * 2. 삭제
     */
    public void removeFreeBoard(long freeboardId) {

        // 1. 게시글 존재 여부 획인
        FreeBoard freeBoard = verifyFreeBoard(freeboardId);

        // 2. 작성자와 로그인한 멤버 비교
        memberService.verifiedAuthenticatedMember(freeBoard.getMember().getMemberId());

        // 3. 삭제
        freeBoardRepository.delete(freeBoard);
    }

    // 게시글이 존재 여부 검증 메서드
    public FreeBoard verifyFreeBoard(long freeboardId) {

        Optional<FreeBoard> optionalFreeBoard = freeBoardRepository.findById(freeboardId);

        return optionalFreeBoard.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.FREEBOARD_NOT_FOUND));
    }

    // 조회수 증가 메서드
    private void addViews(FreeBoard freeBoard) {

        freeBoard.setViewCount(freeBoard.getViewCount() + 1);
        freeBoardRepository.save(freeBoard);
    }

    // 페이지네이션 정렬 기준 선택 메서드
    private PageRequest sortedBy(int page, int size, String sort) {

        if (sort.equals("최신순")) {
            return PageRequest.of(page - 1, size, Sort.by("freeBoardId").descending());
        } else if (sort.equals("등록순")) {
            return PageRequest.of(page - 1, size, Sort.by("freeBoardId").ascending());
        } else if (sort.equals("인기순")) {
            return PageRequest.of(page - 1, size, Sort.by("viewCount", "freeBoardId").descending());
        } else {
            return PageRequest.of(page - 1, size, Sort.by("freeBoardId").ascending());
        }
    }

    public void likeFreeBoard(Long freeBoardId, String authorizationToken) {

        FreeBoard findfreeBoard = verifyFreeBoard(freeBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        boolean isAlreadyLiked = currentMember.getLikes().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Like::getFreeBoard)
                .filter(Objects::nonNull) // null인 FreeBoard 필터링
                .anyMatch(freeBoard -> findfreeBoard.getFreeBoardId().equals(freeBoard.getFreeBoardId()));

        if (isAlreadyLiked) {
            throw new BusinessLogicException(ExceptionCode.LIKE_ALREADY_EXISTS);
        }

        // 게시물의 likeCount 증가
        findfreeBoard.setLikeCount(findfreeBoard.getLikeCount() + 1);
        freeBoardRepository.save(findfreeBoard);

        Like like = new Like();
        like.setBoardType(Like.BoardType.FREEBOARD);
        like.setMember(currentMember);
        like.setFreeBoard(findfreeBoard);
        likeRepository.save(like);

        // 현재 사용자의 likes 컬렉션에 좋아요 추가
        currentMember.getLikes().add(like);
        memberRepository.save(currentMember);
    }

    public void unlikeFreeBoard(Long freeBoardId, String authorizationToken) {

        FreeBoard findfreeBoard = verifyFreeBoard(freeBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        Optional<Set<Like>> likes = Optional.ofNullable(currentMember.getLikes());

        Set<Like> foundLikes = likes.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getFreeBoard() != null && l.getFreeBoard().getFreeBoardId().equals(findfreeBoard.getFreeBoardId()))
                .collect(Collectors.toSet());

        if (foundLikes.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND);
        }

        // 게시글의 likeCount 감소
        if (findfreeBoard.getLikeCount() > 0) {
            findfreeBoard.setLikeCount(findfreeBoard.getLikeCount() - 1);
            freeBoardRepository.save(findfreeBoard);
        }

        // 현재 사용자의 likes 컬렉션에서 좋아요 삭제
        for (Like foundLike : foundLikes) {
            currentMember.getLikes().remove(foundLike);
            likeRepository.delete(foundLike);
        }

        memberRepository.save(currentMember);
    }

    public void bookmarkFreeBoard(Long freeBoardId, String authorizationToken) {

        FreeBoard findfreeBoard = verifyFreeBoard(freeBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        boolean isAlreadyBookMarked = currentMember.getBookmarks().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Bookmark::getFreeBoard)
                .filter(Objects::nonNull) // null인 FeedbackBoard 필터링
                .anyMatch(freeBoard -> findfreeBoard.getFreeBoardId().equals(freeBoard.getFreeBoardId()));

        if (isAlreadyBookMarked) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_ALREADY_EXISTS);
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setBoardType(Bookmark.BoardType.FREEBOARD);
        bookmark.setMember(currentMember);
        bookmark.setFreeBoard(findfreeBoard);
        bookmarkRepository.save(bookmark);

        // 현재 사용자의 bookmark 컬렉션에 bookmark 추가
        currentMember.getBookmarks().add(bookmark);
        memberRepository.save(currentMember);
    }

    public void unbookmarkFreeBoard(Long freeBoardId, String authorizationToken) {

        FreeBoard findfreeBoard = verifyFreeBoard(freeBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        Optional<Set<Bookmark>> bookmarks = Optional.ofNullable(currentMember.getBookmarks());

        Set<Bookmark> foundBookmarks = bookmarks.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getFreeBoard() != null && l.getFreeBoard().getFreeBoardId().equals(findfreeBoard.getFreeBoardId()))
                .collect(Collectors.toSet());

        if (foundBookmarks.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND);
        }

        // 현재 사용자의 bookmarks 컬렉션에서 북마크 삭제
        for (Bookmark foundBookmark : foundBookmarks) {
            currentMember.getBookmarks().remove(foundBookmark);
            bookmarkRepository.delete(foundBookmark);
        }

        memberRepository.save(currentMember);
    }

}
