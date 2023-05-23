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
import com.CreatorConnect.server.board.tag.entity.Tag;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.bookmark.repository.BookmarkRepository;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionBoardService {
    private final PromotionBoardMapper mapper;
    private final PromotionBoardRepository promotionBoardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    private final BookmarkRepository bookmarkRepository;

    // 등록
    public PromotionBoardResponseDto.Post createPromotion(PromotionBoardDto.Post postDto) {

        memberService.verifiedAuthenticatedMember(postDto.getMemberId());

        PromotionBoard promotionBoard = mapper.promotionBoardPostDtoToPromotionBoard(postDto);
        Optional<Category> category = categoryRepository.findByCategoryName(postDto.getCategoryName());
        promotionBoard.setCategory(category.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));

        PromotionBoard savePromotionBoard = promotionBoardRepository.save(promotionBoard);

        PromotionBoardResponseDto.Post responseDto = mapper.promotionBoardToPromotionPostResponse(savePromotionBoard);
        return responseDto;

    }

    //수정
    public PromotionBoardResponseDto.Patch updatePromotion(Long promotionBoardId, PromotionBoardDto.Potct patchDto) {

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

        if(patchDto.getCategoryName() != null) {
            categoryService.verifyCategory(patchDto.getCategoryName());

            Optional<Category> category = categoryRepository.findByCategoryName(patchDto.getCategoryName());
            foundPromotionBoard.setCategory(category.orElseThrow(() - >
                    new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        }

        PromotionBoard updatePromotionBoard = promotionBoardRepository.save(foundPromotionBoard);

        //태그 저장


        PromotionBoardResponseDto.Patch responseDto = mapper.promotionBoardPatchDtoToPromotionBoard(foundPromotionBoard());

//        responseDto.setTag();
        return  responseDto;
    }

    // 개별 조회
    public PromotionBoardResponseDto.Details responsePronotion(Long promotionBoardId){

        PromotionBoard foundPromotionBoard = findVerifiedPromotionBoard(promotionBoardId);

        // 조회수 증가
        addViews(foundPromotionBoard);

        return respose;
    }

    //삭제
    public void deletePromotion(Long promotionBoardId) {
        PromotionBoard promotionBoard = findVerifiedPromotionBoard(promotionBoardId);

        memberService.verifiedAuthenticatedMember(promotionBoard.getMemberId());

        promotionBoardRepository.delete(promotionBoard);
    }

    //프로모션 아이디로 프로모션 찾는 메서드
    public PromotionBoard findVerifiedPromotionBoard(Long promotionBoardId){
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
        if(Objects.equals(sort,"최신순")){
            return PageRequest.of(page - 1, size, Sort.by("promotionBoardId").descending());
        } else if(Objects.equals(sort,"등록순")){
            return PageRequest.of(page - 1, size, Sort.by("promotionBoardId").ascending());
        } else if(Objects.equals(sort,"인기순")){
            return PageRequest.of(page - 1, size, Sort.by("viewCount","promotionBoardId").descending());
        } else {
            return PageRequest.of(page - 1, size, Sort.by("promotionBoardId").descending());
        }
    }

    public void bookmarkPromotionBoard(Long promotionBoardId) {

        PromotionBoard findpromotionBoard = findVerifiedPromotionBoard(promotionBoardId);
        Member currentMember = memberService.getLoggedinMember();

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        boolean isAlreadyBookMarked = currentMember.getBookmarks().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Bookmark::getPromotionBoard)
                .filter(Objects::nonNull) // null인 PromotionBoard 필터링
                .anyMatch(promotionBoard -> findpromotionBoard.getPromotionBoardId().equals(promotionBoard.getPromotionBoardId()))

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

    public void unbookmarkPromotionBoard(Long promotionBoardId) {

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
