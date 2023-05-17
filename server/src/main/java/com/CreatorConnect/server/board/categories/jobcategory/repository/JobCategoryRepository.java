package com.CreatorConnect.server.board.categories.jobcategory.repository;

import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
    Optional<JobCategory> findByJobCategoryName(String jobCategoryName);
}

