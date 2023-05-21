package com.CreatorConnect.server.youtubeapi.entity;

import com.CreatorConnect.server.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class VideoEntity extends Auditable {
    @EmbeddedId
    private VideoPK videoPK;
    @Column
    private String youtubeUrl;
    @Column
    private String thumbnailUrl;
    @Column(columnDefinition = "TEXT")
    private String title;

}

