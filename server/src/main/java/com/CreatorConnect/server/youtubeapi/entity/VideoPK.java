package com.CreatorConnect.server.youtubeapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPK implements Serializable  {
    @Column(name = "video_category")
    private String videoCategory;
    @Column(name = "video_id")
    private Long videoId;
}
