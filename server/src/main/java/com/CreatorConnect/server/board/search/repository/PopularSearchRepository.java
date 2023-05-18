package com.CreatorConnect.server.board.search.repository;

import com.CreatorConnect.server.board.search.entity.PopularSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PopularSearchRepository extends JpaRepository<PopularSearch, Long> {
    PopularSearch findByKeyword(String keyword);

    @Query(value = "SELECT keyword FROM PopularSearch ORDER BY searchCount DESC",
            countQuery = "SELECT COUNT(1) FROM PopularSearch")
    Page<String> getPopularSearchKeywords(Pageable pageable);

}
