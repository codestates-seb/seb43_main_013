package com.CreatorConnect.server.board.promotionboard.entity;

import com.CreatorConnect.server.board.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String link;
    @Column
    private String channelName;
    @Column
    private String subscriberCount;
    @Column
    private String content;



}
