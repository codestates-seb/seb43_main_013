package com.CreatorConnect.server.board.promotionboard.service;

import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.categories.category.repository.CategoryRepository;
import com.CreatorConnect.server.board.categories.category.service.CategoryService;
import com.CreatorConnect.server.board.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardDto;
import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardResponseDto;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import com.CreatorConnect.server.board.promotionboard.mapper.PromotionBoardMapper;
import com.CreatorConnect.server.board.promotionboard.repository.PromotionBoardRepository;
import com.CreatorConnect.server.board.tag.dto.TagDto;
import com.CreatorConnect.server.board.tag.entity.Tag;
import com.CreatorConnect.server.board.tag.mapper.TagMapper;
import com.CreatorConnect.server.board.tag.service.PromotionBoardTagService;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionBoardService {
    private final PromotionBoardMapper mapper;
    private final PromotionBoardRepository promotionBoardRepository;
    private final PromotionBoardTagService promotionBoardTagService;
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
        List<Tag> createTags = promotionBoardTagService.createPromotionBoardTag(tags, savePromotionBoard);

        PromotionBoardResponseDto.Post responseDto = mapper.promotionBoardToPromotionPostResponse(savePromotionBoard);
        return responseDto;

    }

    //수정
    public PromotionBoardResponseDto.Patch updatePromotion(Long promotionBoardId, PromotionBoardDto.Patch patchDto) {

        PromotionBoard promotionBoard = mapper.promotionBoardPatchDtoToPromotionBoard(patchDto);
        promotionBoard.setPromotionBoardId(promotionBoardId);

        PromotionBoard foundPromotionBoard = findVerifiedPromotionBoard(promotionBoard.getPromotionBoardId());

        memberService.verifiedAuthenticatedMember(foundPromotionBoard.getMemberId());

        // 수정
        Optional.ofNullable(promotionBoard.getTitle())
                .ifPresent(title -> foundPromotionBoard.setTitle(title));
        Optional.ofNullable(promotionBoard.getLink())
                .ifPresent(link -> foundPromotionBoard.setLink(link));
        Optional.ofNullable(promotionBoard.getContent())
                .ifPresent(content -> foundPromotionBoard.setContent(content));
        Optional.ofNullable(promotionBoard.getChannelName())
                .ifPresent(channelName -> foundPromotionBoard.setChannelName(channelName));
        Optional.ofNullable(promotionBoard.getSubscriberCount())
                .ifPresent(subscribeCount -> foundPromotionBoard.setSubscriberCount(subscribeCount));


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
        List<Tag> updatedTags = promotionBoardTagService.updatePromotionBoardTag(tags, updatedPromotionBoard);


        PromotionBoardResponseDto.Patch responseDto = mapper.promotionBoardToPromotionPatchResponse(updatedPromotionBoard);
        responseDto.setTags(tagMapper.tagsToTagResponseDto(updatedTags));
        return responseDto;
    }

    // 개별 조회
    public PromotionBoardResponseDto.Details responsePromotion(Long promotionBoardId, HttpServletRequest request) {

        PromotionBoard foundPromotionBoard = findVerifiedPromotionBoard(promotionBoardId);

        List<TagDto.TagInfo> tags = foundPromotionBoard.getTagBoards().stream()
                .map(tagToPromotionBoard -> {
                    TagDto.TagInfo tagInfo = tagMapper.tagToTagToBoard(tagToPromotionBoard.getTag());
                    return tagInfo;
                })
                .collect(Collectors.toList());

        // 조회수 증가
        addViews(foundPromotionBoard);
        // 로그인한 멤버
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
                            .anyMatch(bookmark -> foundPromotionBoard.equals(bookmark.getPromotionBoard()));
                }

                // 게시물을 좋아요한 경우
                if (loggedinMember.getLikes() != null) {
                    liked = loggedinMember.getLikes().stream()
                            .anyMatch(like -> foundPromotionBoard.equals(like.getPromotionBoard()));
                }
            }
        }
        // 매핑
        PromotionBoardResponseDto.Details response = mapper.promotionBoardToResponse(foundPromotionBoard, tags);
        response.setBookmarked(bookmarked);
        response.setLiked(liked);

        return response;
    }

    // 목록 조회
    public PromotionBoardResponseDto.Multi<PromotionBoardResponseDto.Details> responsePromotions(String sort, int page, int size,
                                                                                                 HttpServletRequest request){
        // Page 생성 - 최신순, 등록순, 인기순
        // 기본값 = 최신순
        Page<PromotionBoard> promotionBoards = promotionBoardRepository.findAll(sortedPageRequest(sort, page, size));

        // pageInfo 가져오기
        PromotionBoardResponseDto.PageInfo pageInfo = new PromotionBoardResponseDto.PageInfo(promotionBoards.getNumber() + 1, promotionBoards.getSize(), promotionBoards.getTotalElements(), promotionBoards.getTotalPages());

        // 로그인한 멤버
        String accessToken = request.getHeader("Authorization");
        Member loggedinMember = null;

        if (accessToken != null) {
            loggedinMember = memberService.getLoggedinMember(accessToken);
        }

        List<PromotionBoardResponseDto.Details> responses = getLoginResponseList(promotionBoards, loggedinMember);

        return new PromotionBoardResponseDto.Multi<>(responses, pageInfo);
    }

    public PromotionBoardResponseDto.Multi<PromotionBoardResponseDto.Details> getPromotionByCategory(Long promotionCategoryId, String sort, int page, int size, HttpServletRequest request){
        // page생성 - 카테고리 ID로 검색 후 정렬 적용
        Page<PromotionBoard> promotionBoards = promotionBoardRepository.findPromotionCategoryId(promotionCategoryId, sortedPageRequest(sort, page, size));

        // pageInfo 가져오기
        PromotionBoardResponseDto.PageInfo pageInfo = new PromotionBoardResponseDto.PageInfo(promotionBoards.getNumber() + 1, promotionBoards.getSize(), promotionBoards.getTotalElements(), promotionBoards.getTotalPages());

        // 로그인한 멤버 후 게시글 목록
        String accessToken = request.getHeader("Authorization");
        Member loggedinMember = null;

        if (accessToken != null) {
            loggedinMember = memberService.getLoggedinMember(accessToken);
        }

        List<PromotionBoardResponseDto.Details> responses = getLoginResponseList(promotionBoards, loggedinMember);

        return new PromotionBoardResponseDto.Multi<>(responses, pageInfo);
    }

    //삭제
    public void deletePromotion(Long promotionBoardId) {

        PromotionBoard promotionBoard = findVerifiedPromotionBoard(promotionBoardId);

        memberService.verifiedAuthenticatedMember(promotionBoard.getMemberId());

        promotionBoardRepository.delete(promotionBoard);
    }

    // 프로모션 아이디로 프로모션 찾는 메서드
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
    private List<PromotionBoardResponseDto.Details> getLoginResponseList(Page<PromotionBoard> promotionBoards, Member loggedinMember) {
        return promotionBoards.getContent().stream().map(promotionBoard -> {
            List<TagDto.TagInfo> tags = promotionBoard.getTagBoards().stream()
                    .map(tagToPromotionBoard -> tagMapper.tagToTagToBoard(tagToPromotionBoard.getTag()))
                    .collect(Collectors.toList());

            PromotionBoardResponseDto.Details response = mapper.prmotionBoardToPromotionBoardDetailsResponse(promotionBoard);
            response.setTags(tags);

            if (loggedinMember != null) {
                boolean bookmarked = loggedinMember.getBookmarks().stream()
                        .anyMatch(bookmark -> promotionBoard.equals(bookmark.getPromotionBoard()));
                boolean liked = loggedinMember.getLikes().stream()
                        .anyMatch(like -> promotionBoard.equals(like.getPromotionBoard()));
                response.setBookmarked(bookmarked);
                response.setLiked(liked);
            }

            return response;
        }).collect(Collectors.toList());
    }

    //북마크
    public void bookmarkPromotionBoard(Long promotionBoardId, String authorizationToken) {

        PromotionBoard findpromotionBoard = findVerifiedPromotionBoard(promotionBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

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
        bookmark.setBoardType(Bookmark.BoardType.PROMOTIONBOARD);
        bookmark.setMember(currentMember);
        bookmark.setPromotionBoard(findpromotionBoard);
        bookmarkRepository.save(bookmark);

        // 현재 사용자의 bookmark 컬렉션에 bookmark 추가
        currentMember.getBookmarks().add(bookmark);
        memberRepository.save(currentMember);
    }

    public void unbookmarkpromotionBoard(Long promotionBoardId, String authorizationToken) {

        PromotionBoard promotionBoard = findVerifiedPromotionBoard(promotionBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

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

    public void likePromotionBoard (Long promotionBoardId, String authorizationToken) {

        PromotionBoard findpromotionBoard = findVerifiedPromotionBoard(promotionBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        boolean isAlreadyLiked = currentMember.getLikes().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Like::getPromotionBoard)
                .filter(Objects::nonNull) // null인 FreeBoard 필터링
                .anyMatch(promotionBoard -> findpromotionBoard.getPromotionBoardId().equals(promotionBoard.getPromotionBoardId()));

        if (isAlreadyLiked) {
            throw new BusinessLogicException(ExceptionCode.LIKE_ALREADY_EXISTS);
        }

        // 게시물의 likeCount 증가
        findpromotionBoard.setLikeCount(findpromotionBoard.getLikeCount() + 1);
        promotionBoardRepository.save(findpromotionBoard);

        Like like = new Like();
        like.setBoardType(Like.BoardType.PROMOTIONBOARD);
        like.setMember(currentMember);
        like.setPromotionBoard(findpromotionBoard);
        likeRepository.save(like);

        // 현재 사용자의 likes 컬렉션에 좋아요 추가
        currentMember.getLikes().add(like);
        memberRepository.save(currentMember);
    }

    public void unlikePromotionBoard(Long promotionBoardId, String authorizationToken) {

        PromotionBoard findPromotionBoard = findVerifiedPromotionBoard(promotionBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        Optional<Set<Like>> likes = Optional.ofNullable(currentMember.getLikes());

        Set<Like> foundLikes = likes.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getPromotionBoard() != null && l.getPromotionBoard().getPromotionBoardId().equals(findPromotionBoard.getPromotionBoardId()))
                .collect(Collectors.toSet());

        if (foundLikes.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND);
        }

        // 게시글의 likeCount 감소
        if (findPromotionBoard.getLikeCount() > 0) {
            findPromotionBoard.setLikeCount(findPromotionBoard.getLikeCount() - 1);
            promotionBoardRepository.save(findPromotionBoard);
        }

        // 현재 사용자의 likes 컬렉션에서 좋아요 삭제
        for (Like foundLike : foundLikes) {
            currentMember.getLikes().remove(foundLike);
            likeRepository.delete(foundLike);
        }

        memberRepository.save(currentMember);
    }

}
