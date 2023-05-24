package com.CreatorConnect.server.board.tag.entity;

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
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @Column
    private String tagName; // 태그

    @OneToMany(mappedBy = "tag")
    private List<TagToFreeBoard> tagBoardList = new ArrayList<>();


//    public Tag(String tagName) {
//        this.tagName = tagName;
//        this.tagBoardList = new ArrayList<>();
//    }
}
