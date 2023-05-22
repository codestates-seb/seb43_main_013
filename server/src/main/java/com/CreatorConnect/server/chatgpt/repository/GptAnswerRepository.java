package com.CreatorConnect.server.chatgpt.repository;

import com.CreatorConnect.server.chatgpt.entity.GptAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GptAnswerRepository extends JpaRepository<GptAnswer, Long> {
}
