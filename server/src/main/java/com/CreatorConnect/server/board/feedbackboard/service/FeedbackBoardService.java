package com.CreatorConnect.server.board.feedbackboard.service;

import com.CreatorConnect.server.board.feedbackboard.repository.FeedbackBoardRepository;
import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.categories.category.repository.CategoryRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.board.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.board.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.feedbackboard.mapper.FeedbackBoardMapper;
import com.CreatorConnect.server.board.categories.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.board.categories.feedbackcategory.repository.FeedbackCategoryRepository;
import com.CreatorConnect.server.board.categories.category.service.CategoryService;
import com.CreatorConnect.server.board.categories.feedbackcategory.service.FeedbackCategoryService;
import com.CreatorConnect.server.board.tag.dto.TagDto;
import com.CreatorConnect.server.board.tag.entity.Tag;
import com.CreatorConnect.server.board.tag.mapper.TagMapper;
import com.CreatorConnect.server.board.tag.service.FeedbackBoardTagService;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.bookmark.repository.BookmarkRepository;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.entity.Like;
import com.CreatorConnect.server.member.like.repository.LikeRepository;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackBoardService {
    private final FeedbackBoardMapper mapper;
    private final FeedbackBoardRepository feedbackBoardRepository;
    private final FeedbackCategoryRepository feedbackCategoryRepository;
    private final FeedbackBoardTagService feedbackBoardTagService;
    private final TagMapper tagMapper;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;

    // 등록
    public FeedbackBoardResponseDto.Post createFeedback(FeedbackBoardDto.Post postDto) {

        // post dto memberId 와 로그인 멤버 id 비교
        memberService.verifiedAuthenticatedMember(postDto.getMemberId());

        // Dto-Entity 변환
        FeedbackBoard feedbackBoard = mapper.feedbackBoardPostDtoToFeedbackBoard(postDto);
        Optional<Category> category = categoryRepository.findByCategoryName(postDto.getCategoryName());
        feedbackBoard.setCategory(category.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        Optional<FeedbackCategory> feedbackCategory = feedbackCategoryRepository.findByFeedbackCategoryName(postDto.getFeedbackCategoryName());
        feedbackBoard.setFeedbackCategory(feedbackCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_CATEGORY_NOT_FOUND)));

        //저장
        FeedbackBoard savedfeedbackBoard = feedbackBoardRepository.save(feedbackBoard);

        // 태그 저장
        List<Tag> tags = tagMapper.tagPostDtosToTag(postDto.getTags());
        List<Tag> createTags = feedbackBoardTagService.createFeedbackBoardTag(tags, savedfeedbackBoard);

        // Entity-Dto 변환 후 리턴
        FeedbackBoardResponseDto.Post responseDto = mapper.feedbackBoardToFeedbackBoardPostResponse(savedfeedbackBoard);
        return responseDto;
    }

    // 수정
    public FeedbackBoardResponseDto.Patch updateFeedback(Long feedbackBoardId, FeedbackBoardDto.Patch patchDto){

        // Dto-Entity 변환
        FeedbackBoard feedbackBoard = mapper.feedbackBoardPatchDtoToFeedbackBoard(patchDto);
        feedbackBoard.setFeedbackBoardId(feedbackBoardId);

        // Dto의 Id값으로 데이터베이스에 있는 Entity 찾기
        FeedbackBoard foundFeedbackBoard = findVerifiedFeedbackBoard(feedbackBoard.getFeedbackBoardId());

        // 글 작성한 멤버가 현재 로그인한 멤버와 같은지 확인
        memberService.verifiedAuthenticatedMember(foundFeedbackBoard.getMemberId());

        // 찾은 Entity의 값 변경
        Optional.ofNullable(feedbackBoard.getTitle())
                .ifPresent(foundFeedbackBoard::setTitle);
        Optional.ofNullable(feedbackBoard.getLink())
                .ifPresent(foundFeedbackBoard::setLink);
        Optional.ofNullable(feedbackBoard.getContent())
                .ifPresent(foundFeedbackBoard::setContent);

        // 카테고리를 수정할 경우 카테고리 유효성 검증 (변경한 카테고리가 존재하는 카테고리?)
        if (patchDto.getCategoryName() != null) { // 카테고리 변경이 된 경우
            categoryService.verifyCategory(patchDto.getCategoryName()); // 수정된 카테고리 존재 여부 확인
            // 카테고리 수정
            Optional<Category> category = categoryRepository.findByCategoryName(patchDto.getCategoryName());
            foundFeedbackBoard.setCategory(category.orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        }

        // 피드백 카테고리를 수정할 경우 카테고리 유효성 검증
        if (patchDto.getFeedbackCategoryName() != null) { // 피드백 카테고리가 변경이 된경우
            Optional<FeedbackCategory> feedbackCategory = feedbackCategoryRepository.findByFeedbackCategoryName(patchDto.getFeedbackCategoryName());
            foundFeedbackBoard.setFeedbackCategory(feedbackCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_CATEGORY_NOT_FOUND)));
        }

        // 저장
        FeedbackBoard updatedFeedbackBoard = feedbackBoardRepository.save(foundFeedbackBoard);

        // 태그 저장
        List<Tag> tags = tagMapper.tagPostDtosToTag(patchDto.getTags());
        List<Tag> updatedTags = feedbackBoardTagService.updateFeedbackBoardTag(tags, updatedFeedbackBoard);

        // Entity-Dto 변환 후 리턴
        FeedbackBoardResponseDto.Patch responseDto = mapper.feedbackBoardToFeedbackBoardPatchResponse(updatedFeedbackBoard);
        responseDto.setTags(tagMapper.tagsToTagResponseDto(updatedTags));
        return responseDto;
    }

    // 개별 조회
    public FeedbackBoardResponseDto.Details responseFeedback(Long feedbackBoardId){

        // 클라이언트에서 보낸 ID값으로 Entity 조회
        FeedbackBoard foundFeedbackBoard = findVerifiedFeedbackBoard(feedbackBoardId);

        // 게시글에 있는 태그 추가
        List<TagDto.TagInfo> tags = foundFeedbackBoard.getTagBoards().stream()
                .map(tagToFeedbackBoard -> {
                    TagDto.TagInfo tagInfo = tagMapper.tagToTagToBoard(tagToFeedbackBoard.getTag());
                    return tagInfo;
                })
                .collect(Collectors.toList());

        // 조회수 증가
        addViews(foundFeedbackBoard);

        // 로그인한 멤버
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean bookmarked = false;
        boolean liked = false;

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            Member loggedinMember = memberService.findVerifiedMember(authentication.getName());

            // 게시물을 북마크한 경우
            bookmarked = loggedinMember.getBookmarks().stream()
                    .anyMatch(bookmark -> foundFeedbackBoard.equals(bookmark.getFeedbackBoard()));

            // 게시물을 좋아요한 경우
            liked = loggedinMember.getLikes().stream()
                    .anyMatch(like -> foundFeedbackBoard.equals(like.getFeedbackBoard()));
        }

        // 매핑
        FeedbackBoardResponseDto.Details response = mapper.feedbackBoardToResponse(foundFeedbackBoard, tags);
        response.setBookmarked(bookmarked);
        response.setLiked(liked);

        return response;
    }

    // 목록 조회
    public FeedbackBoardResponseDto.Multi<FeedbackBoardResponseDto.Details> responseFeedbacks(String sort, int page, int size){
        // Page 생성 - 최신순, 등록순, 인기순
        // 기본값 = 최신순
        Page<FeedbackBoard> feedbackBoardsPage = feedbackBoardRepository.findAll(sortedPageRequest(sort, page, size));

        // pageInfo 가져오기
        FeedbackBoardResponseDto.PageInfo pageInfo = new FeedbackBoardResponseDto.PageInfo(feedbackBoardsPage.getNumber() + 1, feedbackBoardsPage.getSize(), feedbackBoardsPage.getTotalElements(), feedbackBoardsPage.getTotalPages());

        // 태그 정보 적용
        List<FeedbackBoardResponseDto.Details> responses = getResponseList(feedbackBoardsPage);

        return new FeedbackBoardResponseDto.Multi<>(responses, pageInfo);
    }

    // 피드백 카테고리로 목록 조회
    public FeedbackBoardResponseDto.Multi<FeedbackBoardResponseDto.Details> responseFeedbacksByCategory(Long feedbackCategoryId, String sort, int page, int size){
        // page생성 - 피드백 카테고리 ID로 검색 후 정렬 적용
        Page<FeedbackBoard> feedbackBoardsPage = feedbackBoardRepository.findFeedbackBoardsByFeedbackCategoryId(feedbackCategoryId, sortedPageRequest(sort, page, size));

        // pageInfo 가져오기
        FeedbackBoardResponseDto.PageInfo pageInfo = new FeedbackBoardResponseDto.PageInfo(feedbackBoardsPage.getNumber() + 1, feedbackBoardsPage.getSize(), feedbackBoardsPage.getTotalElements(), feedbackBoardsPage.getTotalPages());

        // 태그 정보 적용
        List<FeedbackBoardResponseDto.Details> responses = getResponseList(feedbackBoardsPage);

        return new FeedbackBoardResponseDto.Multi<>(responses, pageInfo);
    }

    // 피드백 카테고리 - 카테고리별 목록 조회
    public FeedbackBoardResponseDto.Multi<FeedbackBoardResponseDto.Details> responseFeedbacksByCategory(Long feedbackCategoryId, Long categoryId, String sort, int page, int size){
        // page생성 - 피드백 카테고리 ID 와 카테고리 ID로 검색 후 정렬 적용
        Page<FeedbackBoard> feedbackBoardsPage = feedbackBoardRepository.findFeedbackBoardsByFeedbackCategoryIdAndCategoryId(feedbackCategoryId, categoryId, sortedPageRequest(sort, page, size));

        // pageInfo 가져오기
        FeedbackBoardResponseDto.PageInfo pageInfo = new FeedbackBoardResponseDto.PageInfo(feedbackBoardsPage.getNumber() + 1, feedbackBoardsPage.getSize(), feedbackBoardsPage.getTotalElements(), feedbackBoardsPage.getTotalPages());

        // 태그 정보 적용
        List<FeedbackBoardResponseDto.Details> responses = getResponseList(feedbackBoardsPage);

        return new FeedbackBoardResponseDto.Multi<>(responses, pageInfo);
    }

    // 삭제
    public void deleteFeedback(Long feedbackBoardId) {
        // 피드백 ID로 피드백 찾기
        FeedbackBoard feedbackBoard = findVerifiedFeedbackBoard(feedbackBoardId);

        // 글 작성한 멤버가 현재 로그인한 멤버와 같은지 확인
        memberService.verifiedAuthenticatedMember(feedbackBoard.getMemberId());

        // 삭제
        feedbackBoardRepository.delete(feedbackBoard);
    }

    // 피드백 아이디로 피드백 찾는 메서드
    public FeedbackBoard findVerifiedFeedbackBoard(Long feedbackBoardId) {

        Optional<FeedbackBoard> feedbackBoard = feedbackBoardRepository.findById(feedbackBoardId);

        return feedbackBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_NOT_FOUND));
    }

    // 조회수 증가 메서드
    private void addViews(FeedbackBoard feedbackBoard) {

        feedbackBoard.setViewCount(feedbackBoard.getViewCount() + 1);
        feedbackBoardRepository.save(feedbackBoard);
    }

    // 페이지 정렬 메서드
    private PageRequest sortedPageRequest(String sort, int page, int size) {

        if(Objects.equals(sort,"최신순")){
            return PageRequest.of(page - 1, size, Sort.by("feedbackBoardId").descending());
        } else if(Objects.equals(sort,"등록순")){
            return PageRequest.of(page - 1, size, Sort.by("feedbackBoardId").ascending());
        } else if(Objects.equals(sort,"인기순")){
            return PageRequest.of(page - 1, size, Sort.by("viewCount","feedbackBoardId").descending());
        } else {
            return PageRequest.of(page - 1, size, Sort.by("feedbackBoardId").descending());
        }
    }

    // Response에 각 게시글의 태그 적용 메서드
    private List<FeedbackBoardResponseDto.Details> getResponseList(Page<FeedbackBoard> feedbackBoards) {
        return feedbackBoards.getContent().stream().map(feedbackBoard -> {
            List<TagDto.TagInfo> tags = feedbackBoard.getTagBoards().stream()
                    .map(tagToFreeBoard -> tagMapper.tagToTagToBoard(tagToFreeBoard.getTag()))
                    .collect(Collectors.toList());
            return mapper.feedbackBoardToResponse(feedbackBoard, tags);
        }).collect(Collectors.toList());
    }

    public void likeFeedbackBoard(Long feedbackBoardId) {

        FeedbackBoard findfeedbackBoard = findVerifiedFeedbackBoard(feedbackBoardId);
        Member currentMember = memberService.getLoggedinMember();

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        boolean isAlreadyLiked = currentMember.getLikes().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Like::getFeedbackBoard)
                .filter(Objects::nonNull) // null인 FeedbackBoard 필터링
                .anyMatch(feedbackBoard -> findfeedbackBoard.getFeedbackBoardId().equals(feedbackBoard.getFeedbackBoardId()));

        if (isAlreadyLiked) {
            throw new BusinessLogicException(ExceptionCode.LIKE_ALREADY_EXISTS);
        }

        // 게시물의 likeCount 증가
        findfeedbackBoard.setLikeCount(findfeedbackBoard.getLikeCount() + 1);
        feedbackBoardRepository.save(findfeedbackBoard);

        Like like = new Like();
        like.setBoardType(Like.BoardType.FEEDBACKBOARD);
        like.setMember(currentMember);
        like.setFeedbackBoard(findfeedbackBoard);
        likeRepository.save(like);

        // 현재 사용자의 likes 컬렉션에 좋아요 추가
        currentMember.getLikes().add(like);
        memberRepository.save(currentMember);
    }

    public void unlike(Long feedbackBoardId) {

        FeedbackBoard findfeedbackBoard = findVerifiedFeedbackBoard(feedbackBoardId);
        Member currentMember = memberService.getLoggedinMember();

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        Optional<Set<Like>> likes = Optional.ofNullable(currentMember.getLikes());

        Set<Like> foundLikes = likes.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getFeedbackBoard() != null && l.getFeedbackBoard().getFeedbackBoardId().equals(findfeedbackBoard.getFeedbackBoardId()))
                .collect(Collectors.toSet());

        if (foundLikes.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND);
        }

        // 게시글의 likeCount 감소
        if (findfeedbackBoard.getLikeCount() > 0) {
            findfeedbackBoard.setLikeCount(findfeedbackBoard.getLikeCount() - 1);
            feedbackBoardRepository.save(findfeedbackBoard);
        }

        // 현재 사용자의 likes 컬렉션에서 좋아요 삭제
        for (Like foundLike : foundLikes) {
            currentMember.getLikes().remove(foundLike);
            likeRepository.delete(foundLike);
        }

        memberRepository.save(currentMember);
    }

    public void bookmarkFeedbackBoard(Long feedbackBoardId) {

        FeedbackBoard findfeedbackBoard = findVerifiedFeedbackBoard(feedbackBoardId);
        Member currentMember = memberService.getLoggedinMember();

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        boolean isAlreadyBookMarked = currentMember.getBookmarks().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Bookmark::getFeedbackBoard)
                .filter(Objects::nonNull) // null인 FeedbackBoard 필터링
                .anyMatch(feedbackBoard -> findfeedbackBoard.getFeedbackBoardId().equals(feedbackBoard.getFeedbackBoardId()));

        if (isAlreadyBookMarked) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_ALREADY_EXISTS);
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setBoardType(Bookmark.BoardType.FEEDBACKBOARD);
        bookmark.setMember(currentMember);
        bookmark.setFeedbackBoard(findfeedbackBoard);
        bookmarkRepository.save(bookmark);

        // 현재 사용자의 bookmark 컬렉션에 bookmark 추가
        currentMember.getBookmarks().add(bookmark);
        memberRepository.save(currentMember);
    }

    public void unbookmarkFeedbackBoard(Long feedbackBoardId) {

        FeedbackBoard feedbackBoard = findVerifiedFeedbackBoard(feedbackBoardId);
        Member currentMember = memberService.getLoggedinMember();

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        Optional<Set<Bookmark>> bookmarks = Optional.ofNullable(currentMember.getBookmarks());

        Set<Bookmark> foundBookmarks = bookmarks.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getFeedbackBoard() != null && l.getFeedbackBoard().getFeedbackBoardId().equals(feedbackBoard.getFeedbackBoardId()))
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
