package com.CreatorConnect.server.board.search.service;

import com.CreatorConnect.server.board.Board;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.feedbackboard.repository.FeedbackBoardRepository;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.freeboard.repository.FreeBoardRepository;
import com.CreatorConnect.server.board.search.dto.SearchBoardResponseDto;
import com.CreatorConnect.server.board.search.dto.SearchMemberResponseDto;
import com.CreatorConnect.server.board.search.dto.SearchResponseDto;
import com.CreatorConnect.server.board.search.entity.PopularSearch;
import com.CreatorConnect.server.board.search.repository.PopularSearchRepository;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final FreeBoardRepository freeBoardRepository;
    private final FeedbackBoardRepository feedbackBoardRepository;
    private final MemberRepository memberRepository;
    private final PopularSearchRepository popularSearchRepository;

    public SearchService(FreeBoardRepository freeBoardRepository, FeedbackBoardRepository feedbackBoardRepository, MemberRepository memberRepository, PopularSearchRepository popularSearchRepository) {
        this.freeBoardRepository = freeBoardRepository;
        this.feedbackBoardRepository = feedbackBoardRepository;
        this.memberRepository = memberRepository;
        this.popularSearchRepository = popularSearchRepository;
    }

    public Page<SearchResponseDto> searchPosts(String keyword, Pageable pageable) {

        Page<FreeBoard> freeBoardResults = freeBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        Page<FeedbackBoard> feedbackBoardResults = feedbackBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        Page<Member> memberResults = memberRepository.findByNicknameContaining(keyword, pageable);

        // update keyword search count
        updateSearchCount(keyword);

        // Convert the search results to DTOs
        List<SearchResponseDto> mergedResults = mergeSearchResults(freeBoardResults, feedbackBoardResults, memberResults);
        long totalElements = mergedResults.size();

        return new PageImpl<>(mergedResults, pageable, totalElements);
    }

    private void updateSearchCount(String keyword) {
        PopularSearch popularSearch = popularSearchRepository.findByKeyword(keyword);
        if (popularSearch == null) {
            popularSearch = new PopularSearch();
        }
        popularSearch.setKeyword(keyword);
        popularSearch.setSearchCount(popularSearch.getSearchCount() + 1);
        popularSearch.setModifiedAt(LocalDateTime.now());
        popularSearchRepository.save(popularSearch);
    }

    private List<SearchResponseDto> mergeSearchResults(Page<FreeBoard> freeBoardResults, Page<FeedbackBoard> feedbackBoardResults,
                                                       Page<Member> memberResults) {
        List<SearchResponseDto> mergedResults = new ArrayList<>();

        // Convert FreeBoard search results
        for (FreeBoard freeBoard : freeBoardResults.getContent()) {
            SearchBoardResponseDto dto = convertToSearchFreeBoardResponseDto(freeBoard);
            mergedResults.add(dto);
        }

        // Convert FeedbackBoard search results
        for (FeedbackBoard feedbackBoard : feedbackBoardResults.getContent()) {
            SearchBoardResponseDto dto = convertToSearchFeedBackBoardResponseDto(feedbackBoard);
            mergedResults.add(dto);
        }

        // Convert Member search results
        for (Member member : memberResults.getContent()) {
            SearchMemberResponseDto dto = convertToSearchMemberResponseDto(member);
            mergedResults.add(dto);
        }

        return mergedResults;
    }

    private SearchBoardResponseDto convertToSearchFreeBoardResponseDto(FreeBoard board) {
        SearchBoardResponseDto dto = new SearchBoardResponseDto();
        dto.setBoardType("FREEBOARD");
        dto.setId(board.getFreeBoardId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setCommentCount(board.getCommentCount());
        dto.setLikeCount(board.getLikeCount());
        dto.setViewCount(board.getViewCount());
        dto.setCategoryName(board.getCategoryName());
        dto.setMemberId(board.getMember().getMemberId());
        dto.setProfileImageUrl(board.getMember().getProfileImageUrl());
        dto.setCreatedAt(board.getCreatedAt());
        dto.setModifiedAt(board.getModifiedAt());

        return dto;
    }

    private SearchBoardResponseDto convertToSearchFeedBackBoardResponseDto(FeedbackBoard board) {
        SearchBoardResponseDto dto = new SearchBoardResponseDto();
        dto.setBoardType("FEEDBACKBOARD");
        dto.setId(board.getFeedbackBoardId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setCommentCount(board.getCommentCount());
        dto.setLikeCount(board.getLikeCount());
        dto.setViewCount(board.getViewCount());
        dto.setCategoryName(board.getCategoryName());
        dto.setMemberId(board.getMember().getMemberId());
        dto.setProfileImageUrl(board.getMember().getProfileImageUrl());
        dto.setCreatedAt(board.getCreatedAt());
        dto.setModifiedAt(board.getModifiedAt());

        return dto;
    }

    private SearchMemberResponseDto convertToSearchMemberResponseDto(Member member) {
        SearchMemberResponseDto dto = new SearchMemberResponseDto();
        dto.setBoardType("MEMBER");
        dto.setId(member.getMemberId());
        dto.setMemberId(member.getMemberId());
        dto.setEmail(member.getEmail());
        dto.setName(member.getName());
        dto.setNickname(member.getNickname());
        dto.setProfileImageUrl(member.getProfileImageUrl());
        dto.setCreatedAt(member.getCreatedAt());
        dto.setModifiedAt(member.getModifiedAt());

        return dto;
    }

    public Page<String> getPopularSearchKeywords(Pageable pageable) {
        return popularSearchRepository.getPopularSearchKeywords(pageable);
    }

}

