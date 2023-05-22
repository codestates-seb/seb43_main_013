package com.CreatorConnect.server.chatgpt.controller;

import com.CreatorConnect.server.chatgpt.dto.ChatGptCompletionRequestDto;
import com.CreatorConnect.server.chatgpt.dto.ChatGptCompletionResponseDto;
import com.CreatorConnect.server.chatgpt.service.ChatGptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatgpt")
@RequiredArgsConstructor
public class ChatGptController {
    private final ChatGptService chatGptService;


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/ask")
    public ResponseEntity askToGpt(@RequestBody ChatGptCompletionRequestDto request) {
        ChatGptCompletionResponseDto response = chatGptService.createAsk(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
