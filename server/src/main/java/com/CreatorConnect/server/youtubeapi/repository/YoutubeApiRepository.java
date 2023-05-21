package com.CreatorConnect.server.youtubeapi.repository;

import com.CreatorConnect.server.youtubeapi.entity.VideoEntity;
import com.CreatorConnect.server.youtubeapi.entity.VideoPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface YoutubeApiRepository extends JpaRepository<VideoEntity, VideoPK> {
    @Query("select v from VideoEntity v where v.videoPK.videoCategory = :videoCategory")
    Page<VideoEntity> findByVideoCategoryId(@Param("videoCategory") String videoCategory, Pageable pageable);

}