package com.CreatorConnect.server.board.tag.entity;

import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagToPromotionBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long TagToPromotionId;

    @ManyToOne
    @JoinColumn(name = "PROMOTIONBOARD_ID")
    private PromotionBoard promotionBoard;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public TagToPromotionBoard(PromotionBoard promotionBoard, Tag tag) {
        this.promotionBoard = promotionBoard;
        this.tag = tag;
    }
}
