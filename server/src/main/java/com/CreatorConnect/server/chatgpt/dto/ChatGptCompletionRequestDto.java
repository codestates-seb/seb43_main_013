package com.CreatorConnect.server.chatgpt.dto;

import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptCompletionRequestDto {
    private String model; // api 모델
    private String prompt; // 질문 내용
    private Integer maxToken;




    public static CompletionRequest of(ChatGptCompletionRequestDto request) {
        return CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(request.getPrompt())
                .maxTokens(request.getMaxToken())
                .build();
    }
}


