package com.CreatorConnect.server.board.promotionboard.service;

import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.categories.category.repository.CategoryRepository;
import com.CreatorConnect.server.board.categories.category.service.CategoryService;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardDto;
import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardResponseDto;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import com.CreatorConnect.server.board.promotionboard.mapper.PromotionBoardMapper;
import com.CreatorConnect.server.board.promotionboard.repository.PromotionBoardRepository;
import com.CreatorConnect.server.board.tag.dto.TagDto;
import com.CreatorConnect.server.board.tag.entity.Tag;
import com.CreatorConnect.server.board.tag.mapper.TagMapper;
import com.CreatorConnect.server.board.tag.service.PromotionBoardTagservice;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.bookmark.repository.BookmarkRepository;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.repository.LikeRepository;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionBoardService {
    private final PromotionBoardMapper mapper;
    private final PromotionBoardRepository promotionBoardRepository;
    private final PromotionBoardTagservice promotionBoardTagservice;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final TagMapper tagMapper;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;

    // 등록
    public PromotionBoardResponseDto.Post createPromotion(PromotionBoardDto.Post postDto) {

        memberService.verifiedAuthenticatedMember(postDto.getMemberId());

        PromotionBoard promotionBoard = mapper.promotionBoardPostDtoToPromotionBoard(postDto);
        Optional<Category> category = categoryRepository.findByCategoryName(postDto.getCategoryName());
        promotionBoard.setCategory(category.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));

        PromotionBoard savePromotionBoard = promotionBoardRepository.save(promotionBoard);

        List<Tag> tags = tagMapper.tagPostDtosToTag(postDto.getTags());
        List<Tag> createTags = promotionBoardTagservice.createPromotionBoardTag(tags, savePromotionBoard);

        PromotionBoardResponseDto.Post responseDto = mapper.promotionBoardToPromotionPostResponse(savePromotionBoard);
        return responseDto;

    }

    //수정
    public PromotionBoardResponseDto.Patch updatePromotion(Long promotionBoardId, PromotionBoardDto.Patch patchDto) {

        PromotionBoard promotionBoard = mapper.promotionBoardPatchDtoToPromotionBoard(patchDto);
        promotionBoard.setPromotionBoardId(promotionBoardId);

        PromotionBoard foundPromotionBoard = findVerifiedPromotionBoard(promotionBoard.getPromotionBoardId());

        memberService.verifiedAuthenticatedMember(foundPromotionBoard.getMemberId());

        Optional.ofNullable(promotionBoard.getTitle())
                .ifPresent(foundPromotionBoard::setTitle);
        Optional.ofNullable(promotionBoard.getLink())
                .ifPresent(foundPromotionBoard::setLink);
        Optional.ofNullable(promotionBoard.getContent())
                .ifPresent(foundPromotionBoard::setContent);
        // 카테골리 유효성 검증
        if (patchDto.getCategoryName() != null) {
            categoryService.verifyCategory(patchDto.getCategoryName());
            // 카테고리 수정
            Optional<Category> category = categoryRepository.findByCategoryName(patchDto.getCategoryName());
            foundPromotionBoard.setCategory(category.orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        }


        PromotionBoard updatedPromotionBoard = promotionBoardRepository.save(foundPromotionBoard);

        //태그 저장
        List<Tag> tags = tagMapper.tagPostDtosToTag(patchDto.getTags());
        List<Tag> updatedTags = promotionBoardTagservice.updatePromotionBoardTag(tags, updatedPromotionBoard);


        PromotionBoardResponseDto.Patch responseDto = mapper.promotionBoardToPromotionPatchResponse(updatedPromotionBoard);
        responseDto.setTags(tagMapper.tagsToTagResponseDto(updatedTags));
        return responseDto;
    }

    // 개별 조회
    public PromotionBoardResponseDto.Details responsePromotion(Long promotionBoardId) {

        PromotionBoard foundPromotionBoard = findVerifiedPromotionBoard(promotionBoardId);

        List<TagDto.TagInfo> tags = foundPromotionBoard.getTagBoards().stream()
                .map(tagToPromotionBoard -> {
                    TagDto.TagInfo tagInfo = tagMapper.tagToTagToBoard(tagToPromotionBoard.getTag());
                    return tagInfo;
                })
                .collect(Collectors.toList());

        // 조회수 증가
        addViews(foundPromotionBoard);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean bookmarked = false;
        boolean liked = false;

        if (authentication != null && authentication.isAuthenticated() && ! "anonymousUser".equals(authentication.getName())) {
            Member loggedinMember = memberService.findVerifiedMember(authentication.getName());

            // 게시물을 북마크한 경우
            bookmarked = loggedinMember.getBookmarks().stream()
                    .anyMatch(bookmark -> foundPromotionBoard.equals(bookmark.getFeedbackBoard()));

            // 게시물을 좋아요한 경우
            liked = loggedinMember.getLikes().stream()
                    .anyMatch(like -> foundPromotionBoard.equals(like.getFeedbackBoard()));
        }

        // 매핑
        PromotionBoardResponseDto.Details response = mapper.promotionBoardToResponse(foundPromotionBoard, tags);
        response.setBookmarked(bookmarked);
        response.setLiked(liked);

        return response;
    }

    // 목록 조회
    public PromotionBoardResponseDto.Multi<PromotionBoardResponseDto.Details> responsePromotions(String sort, int page, int size){
        // Page 생성 - 최신순, 등록순, 인기순
        // 기본값 = 최신순
        Page<PromotionBoard> promotionBoardsPage = promotionBoardRepository.findAll(sortedPageRequest(sort, page, size));

        // pageInfo 가져오기
        PromotionBoardResponseDto.PageInfo pageInfo = new PromotionBoardResponseDto.PageInfo(promotionBoardsPage.getNumber() + 1, promotionBoardsPage.getSize(), promotionBoardsPage.getTotalElements(), promotionBoardsPage.getTotalPages());

        // 로그인한 멤버
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<PromotionBoardResponseDto.Details> responses = new ArrayList<>();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            Member loggedinMember = memberService.findVerifiedMember(authentication.getName());

            for (PromotionBoard promotionBoard : promotionBoardsPage.getContent()) {
                boolean bookmarked = loggedinMember.getBookmarks().stream()
                        .anyMatch(bookmark -> promotionBoard.equals(bookmark.getFeedbackBoard()));

                boolean liked = loggedinMember.getLikes().stream()
                        .anyMatch(like -> promotionBoard.equals(like.getFeedbackBoard()));

                PromotionBoardResponseDto.Details feedbackResponse = mapper.prmotionBoardToPromotionBoardDetailsResponse(promotionBoard);
                feedbackResponse.setBookmarked(bookmarked);
                feedbackResponse.setLiked(liked);
                responses.add(promotionBoard);
            }
        } else {
            responses = getResponseList(promotionBoardsPage);
        }

        return new PromotionBoardResponseDto.Multi<>(responses, pageInfo);
    }
    //피드백 목록 조회
    public PromotionBoardResponseDto.Multi<PromotionBoardResponseDto.Details> responseFeedbacksByCategory(Long promotioncategory, String sort, int page, int size){
        // page생성 - 피드백 카테고리 ID로 검색 후 정렬 적용
        Page<FeedbackBoard> feedbackBoardsPage = promotionBoardRepository.findFeedbackBoardsByFeedbackCategoryId(feedbackCategoryId, sortedPageRequest(sort, page, size));

        // pageInfo 가져오기
        PromotionBoardResponseDto.PageInfo pageInfo = new PromotionBoardResponseDto.PageInfo(feedbackBoardsPage.getNumber() + 1, feedbackBoardsPage.getSize(), feedbackBoardsPage.getTotalElements(), feedbackBoardsPage.getTotalPages());

        // 로그인한 멤버 후 게시글 목록
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<PromotionBoardResponseDto.Details> responses = new ArrayList<>();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            Member loggedinMember = memberService.findVerifiedMember(authentication.getName());

            for (FeedbackBoard feedbackBoard : feedbackBoardsPage.getContent()) {
                boolean bookmarked = loggedinMember.getBookmarks().stream()
                        .anyMatch(bookmark -> feedbackBoard.equals(bookmark.getPromotionBoard()));

                boolean liked = loggedinMember.getLikes().stream()
                        .anyMatch(like -> feedbackBoard.equals(like.getPromotionBoard()));

                PromotionBoardResponseDto.Details promotionResponse = mapper.prmotionBoardToPromotionBoardDetailsResponse(feedbackBoard);
                promotionResponse.setBookmarked(bookmarked);
                promotionResponse.setLiked(liked);
                responses.add(feedbackResponse);
            }
        } else {
            responses = getResponseList(feedbackBoardsPage);
        }

        return new PromotionBoardResponseDto.Multi<>(responses, pageInfo);
    }

    //삭제
    public void deletePromotion(Long promotionBoardId) {
        PromotionBoard promotionBoard = findVerifiedPromotionBoard(promotionBoardId);

        memberService.verifiedAuthenticatedMember(promotionBoard.getMemberId());

        promotionBoardRepository.delete(promotionBoard);
    }

    //프로모션 아이디로 프로모션 찾는 메서드
    public PromotionBoard findVerifiedPromotionBoard(Long promotionBoardId) {
        Optional<PromotionBoard> promotionBoard = promotionBoardRepository.findById(promotionBoardId);

        return promotionBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_NOT_FOUND));

    }

    // 조회수 증가 메서드
    private void addViews(PromotionBoard promotionBoard) {
        promotionBoard.setViewCount(promotionBoard.getViewCount() + 1);
        promotionBoardRepository.save(promotionBoard);
    }

    //페이지 정렬 메서드
    private PageRequest sortedPageRequest(String sort, int page, int size) {
        if (Objects.equals(sort, "최신순")) {
            return PageRequest.of(page - 1, size, Sort.by("promotionBoardId").descending());
        } else if (Objects.equals(sort, "등록순")) {
            return PageRequest.of(page - 1, size, Sort.by("promotionBoardId").ascending());
        } else if (Objects.equals(sort, "인기순")) {
            return PageRequest.of(page - 1, size, Sort.by("viewCount", "promotionBoardId").descending());
        } else {
            return PageRequest.of(page - 1, size, Sort.by("promotionBoardId").descending());
        }
    }
    // 태그 적용 메서드
    private List<PromotionBoardResponseDto.Details> getResponseList(Page<PromotionBoard> promotionBoards) {
        return promotionBoards.getContent().stream().map(promotionBoard -> {
            List<TagDto.TagInfo> tags = promotionBoard.getTagBoards().stream()
                    .map(tagToPromotionBoard -> tagMapper.tagToTagToBoard(tagToPromotionBoard.getTag()))
                    .collect(Collectors.toList());
            return mapper.promotionBoardToResponse(promotionBoard, tags);
        }).collect(Collectors.toList());
    }

    //북마크
    public void bookmarkPromotionBoard(Long promotionBoardId) {

        PromotionBoard findpromotionBoard = findVerifiedPromotionBoard(promotionBoardId);
        Member currentMember = memberService.getLoggedinMember();

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        boolean isAlreadyBookMarked = currentMember.getBookmarks().stream()
                .filter(Objects::nonNull)
                .map(Bookmark::getPromotionBoard)
                .filter(Objects::nonNull)
                .anyMatch(promotionBoard ->
                        findpromotionBoard.getPromotionBoardId().equals(promotionBoard.getPromotionBoardId())
                );

        if (isAlreadyBookMarked) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_ALREADY_EXISTS);
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setBoardType(Bookmark.BoardType.FEEDBACKBOARD);
        bookmark.setMember(currentMember);
        bookmark.setPromotionBoard(findpromotionBoard);
        bookmarkRepository.save(bookmark);

        // 현재 사용자의 bookmark 컬렉션에 bookmark 추가
        currentMember.getBookmarks().add(bookmark);
        memberRepository.save(currentMember);
    }

    public void unbookmarkpromotionBoard(Long promotionBoardId) {

        PromotionBoard promotionBoard = findVerifiedPromotionBoard(promotionBoardId);
        Member currentMember = memberService.getLoggedinMember();

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        Optional<Set<Bookmark>> bookmarks = Optional.ofNullable(currentMember.getBookmarks());

        Set<Bookmark> foundBookmarks = bookmarks.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getPromotionBoard() != null && l.getPromotionBoard().getPromotionBoardId().equals(promotionBoard.getPromotionBoardId()))
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
