package com.CreatorConnect.server.board.categories.category.entity;

import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @Column(nullable = false)
    private String categoryName; // 카테고리 이름

    // category - freeboard 일대다 양방향 매핑
    @OneToMany(mappedBy = "category")
    private List<FreeBoard> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FeedbackBoard> feedbackBoards = new ArrayList<>();


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PromotionBoard> promotionBoards = new ArrayList<>();
}
