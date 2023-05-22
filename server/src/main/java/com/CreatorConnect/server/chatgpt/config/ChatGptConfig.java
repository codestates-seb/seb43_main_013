package com.CreatorConnect.server.chatgpt.config;


import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
// 발급받은 ChatGpt API token을 주입하는 설정 클래스
public class ChatGptConfig {
    @Value("${chatgpt.api-key}") // 발급받은 api-key
    private String token;

    @Bean
    public OpenAiService openAiService(){
        log.info("token : {}", token);
        /*
         REST API로 구현 시 response가 전체 답변이 입력된 후 호출되기 때문에 time out이슈 발생 가능
         따라서 Duration.ofSeconds(60)로 wait time을 설정
         */
        return new OpenAiService(token, Duration.ofSeconds(60));
    }
}
