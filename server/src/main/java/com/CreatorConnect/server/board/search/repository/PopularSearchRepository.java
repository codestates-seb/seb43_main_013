package com.CreatorConnect.server.board.search.repository;

import com.CreatorConnect.server.board.search.entity.PopularSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PopularSearchRepository extends JpaRepository<PopularSearch, Long> {
    PopularSearch findByKeyword(String keyword);

    @Query(value = "SELECT keyword FROM PopularSearch ORDER BY searchCount DESC",
            countQuery = "SELECT COUNT(1) FROM PopularSearch")
    Page<String> getPopularSearchKeywords(Pageable pageable);

    @Query(value = "SELECT keyword FROM PopularSearch WHERE searchDate = :date ORDER BY searchCount DESC",
            countQuery = "SELECT COUNT(p) FROM PopularSearch p WHERE p.searchDate = :date")
    Page<String> getDailyPopularSearchKeywords(@Param("date") LocalDate date, Pageable pageable);

    @Query(value = "SELECT keyword FROM PopularSearch WHERE searchDate >= :oneWeekAgo AND searchDate <= :now ORDER BY searchCount DESC",
            countQuery = "SELECT COUNT(1) FROM PopularSearch WHERE searchDate >= :oneWeekAgo AND searchDate <= :now")
    Page<String> getWeeklyPopularSearchKeywords(@Param("oneWeekAgo") LocalDate oneWeekAgo,
                                                @Param("now") LocalDate now,
                                                Pageable pageable);

    @Query(value = "SELECT keyword FROM PopularSearch WHERE searchDate >= :oneMonthAgo AND searchDate <= :now ORDER BY searchCount DESC",
            countQuery = "SELECT COUNT(1) FROM PopularSearch WHERE searchDate >= :oneMonthAgo AND searchDate <= :now")
    Page<String> getMonthlyPopularSearchKeywords(@Param("oneMonthAgo") LocalDate oneMonthAgo,
                                                 @Param("now") LocalDate now,
                                                 Pageable pageable);

    @Modifying
    @Query(value = "UPDATE PopularSearch SET searchCount = searchCount + 1, searchDate = :searchDate WHERE keyword = :keyword")
    void incrementSearchCount(@Param("keyword") String keyword, @Param("searchDate") LocalDate searchDate);

}
