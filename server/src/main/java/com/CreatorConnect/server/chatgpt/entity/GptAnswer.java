package com.CreatorConnect.server.chatgpt.entity;

import com.CreatorConnect.server.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class GptAnswer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(length = 1500, nullable = false)
    private String answer;

    public GptAnswer(String answer) {
        this.answer = answer;
    }
}
