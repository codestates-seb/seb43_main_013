package com.CreatorConnect.server.chatgpt.service;

import com.CreatorConnect.server.chatgpt.dto.ChatGptCompletionRequestDto;
import com.CreatorConnect.server.chatgpt.dto.ChatGptCompletionResponseDto;
import com.CreatorConnect.server.chatgpt.entity.GptAnswer;
import com.CreatorConnect.server.chatgpt.entity.GptQuestion;
import com.CreatorConnect.server.chatgpt.repository.GptAnswerRepository;
import com.CreatorConnect.server.chatgpt.repository.GptQuestionRepository;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatGptService {
    private final OpenAiService openAiService;

    private final GptAnswerRepository answerRepository;

    private final GptQuestionRepository questionRepository;

    /**
     * <gpt에 질문하기>
     * 1. 질문 등록
     * 2. 답변
     * 3. 질문 / 답변 저장
     */
    public ChatGptCompletionResponseDto createAsk(ChatGptCompletionRequestDto request) {
        // 1. 질문 등록
        CompletionResult result = openAiService.createCompletion(ChatGptCompletionRequestDto.of(request));
        ChatGptCompletionResponseDto response = ChatGptCompletionResponseDto.of(result);

        // 2. 답변
        List<String> messages = response.getMessages().stream()
                .map(ChatGptCompletionResponseDto.Message::getText)
                .collect(Collectors.toList());

        // 3. 질문 / 답변 저장
        GptAnswer gptAnswer = saveAnswer(messages);
        saveQuestion(request.getPrompt(), gptAnswer);

        return response;
    }

    private GptAnswer saveAnswer(List<String> messages) {
        String answer = messages.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining());
        return answerRepository.save(new GptAnswer(answer));
    }

    private void saveQuestion(String question, GptAnswer answer) {
        GptQuestion gptQuestion = new GptQuestion(question, answer);
        questionRepository.save(gptQuestion);
    }

}
