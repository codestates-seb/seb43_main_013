package com.CreatorConnect.server.board.search.service;

import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.feedbackboard.repository.FeedbackBoardRepository;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.freeboard.repository.FreeBoardRepository;
import com.CreatorConnect.server.board.search.dto.SearchBoardResponseDto;
import com.CreatorConnect.server.board.search.dto.SearchMemberResponseDto;
import com.CreatorConnect.server.board.search.dto.SearchResponseDto;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final FreeBoardRepository freeBoardRepository;
    private final FeedbackBoardRepository feedbackBoardRepository;
    private final MemberRepository memberRepository;

    public SearchService(FreeBoardRepository freeBoardRepository, FeedbackBoardRepository feedbackBoardRepository, MemberRepository memberRepository) {
        this.freeBoardRepository = freeBoardRepository;
        this.feedbackBoardRepository = feedbackBoardRepository;
        this.memberRepository = memberRepository;
    }

    public Page<SearchResponseDto> searchPosts(String keyword, Pageable pageable) {
        Page<FreeBoard> freeBoardResults = freeBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        Page<FeedbackBoard> feedbackBoardResults = feedbackBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        Page<Member> memberResults = memberRepository.findByNicknameContaining(keyword, pageable);

        List<SearchResponseDto> mergedResults = new ArrayList<>();

        // FreeBoard 검색 결과를 SearchResponseDTO로 변환하여 mergedResults에 추가
        for (FreeBoard freeBoard : freeBoardResults.getContent()) {
            SearchBoardResponseDto dto = new SearchBoardResponseDto();
            dto.setBoardType("FREEBOARD");
            dto.setId(freeBoard.getFreeBoardId());
            dto.setTitle(freeBoard.getTitle());
            dto.setContent(freeBoard.getContent());
            dto.setCommentCount(freeBoard.getCommentCount());
            dto.setLikeCount(freeBoard.getLikeCount());
            dto.setViewCount(freeBoard.getViewCount());
            dto.setCategoryName(freeBoard.getCategoryName());
            dto.setMemberId(freeBoard.getMember().getMemberId());
            dto.setProfileImageUrl(freeBoard.getMember().getProfileImageUrl());
            dto.setCreatedAt(freeBoard.getCreatedAt());
            dto.setModifiedAt(freeBoard.getModifiedAt());
            mergedResults.add(dto);
        }

        // FeedbackBoard 검색 결과를 SearchResponseDTO로 변환하여 mergedResults에 추가
        for (FeedbackBoard feedbackBoard : feedbackBoardResults.getContent()) {
            SearchBoardResponseDto dto = new SearchBoardResponseDto();
            dto.setBoardType("FEEDBACKBOARD");
            dto.setId(feedbackBoard.getFeedbackBoardId());
            dto.setTitle(feedbackBoard.getTitle());
            dto.setContent(feedbackBoard.getContent());
            dto.setCommentCount(feedbackBoard.getCommentCount());
            dto.setLikeCount(feedbackBoard.getLikeCount());
            dto.setViewCount(feedbackBoard.getViewCount());
            dto.setCategoryName(feedbackBoard.getCategoryName());
            dto.setMemberId(feedbackBoard.getMember().getMemberId());
            dto.setProfileImageUrl(feedbackBoard.getMember().getProfileImageUrl());
            dto.setCreatedAt(feedbackBoard.getCreatedAt());
            dto.setModifiedAt(feedbackBoard.getModifiedAt());
            mergedResults.add(dto);
        }

        // Member 검색 결과를 SearchResponseDTO로 변환하여 mergedResults에 추가
        for (Member member : memberResults.getContent()) {
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
            mergedResults.add(dto);
        }

        return new PageImpl<>(mergedResults, pageable, mergedResults.size());
    }
}
