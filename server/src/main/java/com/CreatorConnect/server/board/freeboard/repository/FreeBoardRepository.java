package com.CreatorConnect.server.board.freeboard.repository;

import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    List<FreeBoard> findByCategory(Category category);
    Page<FreeBoard> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    // categoryId에 해당하는 게시글 목록 조회
    @Query("select f from FreeBoard f where f.category.categoryId = :categoryId")
    Page<FreeBoard> findFreeBoardsByCategoryId(@Param("categoryId") long categoryId, Pageable pageable);

}