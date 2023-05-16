package com.CreatorConnect.server.board.search.service;

import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.feedbackboard.repository.FeedbackBoardRepository;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.freeboard.repository.FreeBoardRepository;
import com.CreatorConnect.server.board.search.dto.SearchResponseDTO;
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

    public SearchService(FreeBoardRepository freeBoardRepository, FeedbackBoardRepository feedbackBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
        this.feedbackBoardRepository = feedbackBoardRepository;
    }

    public Page<SearchResponseDTO> searchPosts(String keyword, Pageable pageable) {
        Page<FreeBoard> freeBoardResults = freeBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        Page<FeedbackBoard> feedbackBoardResults = feedbackBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);

        List<SearchResponseDTO> mergedResults = new ArrayList<>();

        // FreeBoard 검색 결과를 SearchResponseDTO로 변환하여 mergedResults에 추가
        for (FreeBoard freeBoard : freeBoardResults.getContent()) {
            SearchResponseDTO dto = new SearchResponseDTO();
            dto.setBoardType("FREEBOARD");
            dto.setId(freeBoard.getFreeBoardId());
            dto.setTitle(freeBoard.getTitle());
            dto.setContent(freeBoard.getContent());
            mergedResults.add(dto);
        }

        // FeedbackBoard 검색 결과를 SearchResponseDTO로 변환하여 mergedResults에 추가
        for (FeedbackBoard feedbackBoard : feedbackBoardResults.getContent()) {
            SearchResponseDTO dto = new SearchResponseDTO();
            dto.setBoardType("FEEDBACKBOARD");
            dto.setId(feedbackBoard.getFeedbackBoardId());
            dto.setTitle(feedbackBoard.getTitle());
            dto.setContent(feedbackBoard.getContent());
            mergedResults.add(dto);
        }

        return new PageImpl<>(mergedResults, pageable, mergedResults.size());
    }
}
