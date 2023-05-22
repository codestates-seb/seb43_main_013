package com.CreatorConnect.server.chatgpt.repository;

import com.CreatorConnect.server.chatgpt.entity.GptQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GptQuestionRepository extends JpaRepository<GptQuestion, Long> {
}
