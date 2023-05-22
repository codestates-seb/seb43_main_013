package com.CreatorConnect.server.chatgpt.dto;

import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptCompletionResponseDto {
    private List<Message> messages; // gpt의 답변

    private Usage usage;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message{
        private String text;

        private Integer index;

        private String finishReason;

        public static Message of(CompletionChoice choice) {
            return new Message(
                    choice.getText(),
                    choice.getIndex(),
                    choice.getFinish_reason()
            );
        }
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage{
        private Long promptTokens;

        private Long completionTokens;

        private Long totalTokens;

        public static Usage of(com.theokanning.openai.Usage usage) {
            return new Usage(
                    usage.getPromptTokens(),
                    usage.getCompletionTokens(),
                    usage.getTotalTokens()
            );
        }
    }

    public static List<Message> messageResponses(List<CompletionChoice> choices){
        return choices.stream()
                .map(Message::of)
                .collect(Collectors.toList());
    }

    public static ChatGptCompletionResponseDto of(CompletionResult result) {
        return new ChatGptCompletionResponseDto(
                messageResponses(result.getChoices()),
                Usage.of(result.getUsage())
        );
    }

}
