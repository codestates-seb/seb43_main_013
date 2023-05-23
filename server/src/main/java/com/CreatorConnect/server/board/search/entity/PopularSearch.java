package com.CreatorConnect.server.board.search.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.audit.LocalDateTimeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class PopularSearch extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long popularSearchId;

    @Column
    private String keyword;

    @Column
    private Integer searchCount = 0;

    @Column
    private LocalDate searchDate;

}
