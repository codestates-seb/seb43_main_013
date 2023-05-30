package com.CreatorConnect.server.youtubeapi.mapper;

import com.CreatorConnect.server.youtubeapi.dto.YoutubeApiDto;
import com.CreatorConnect.server.youtubeapi.entity.VideoEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class YoutubeApiMapper {

    public YoutubeApiDto.Details videoEntityToYoutubeApiDtoDetailsResponse(VideoEntity videoEntity){
        YoutubeApiDto.Details details = new YoutubeApiDto.Details();
        details.setVideoCategory(videoEntity.getVideoPK().getVideoCategory());
        details.setVideoId(videoEntity.getVideoPK().getVideoId());
        details.setYoutubeId(videoEntity.getYoutubeId());
        details.setYoutubeUrl(videoEntity.getYoutubeUrl());
        details.setThumbnailUrl(videoEntity.getThumbnailUrl());
        details.setTitle(videoEntity.getTitle());
        return details;
    }

    public List<YoutubeApiDto.Details> videoEntitiesToVideoEntitiesDetailsResponses(List<VideoEntity> videoEntities) {
        if ( videoEntities == null ) {
            return null;
        }

        List<YoutubeApiDto.Details> list = new ArrayList<YoutubeApiDto.Details>( videoEntities.size() );
        for ( VideoEntity videoEntity : videoEntities ) {
            list.add( videoEntityToYoutubeApiDtoDetailsResponse( videoEntity ) );
        }

        return list;
    }
}
