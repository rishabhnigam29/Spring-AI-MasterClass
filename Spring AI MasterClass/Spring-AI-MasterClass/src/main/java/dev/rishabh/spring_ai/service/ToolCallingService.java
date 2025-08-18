package dev.rishabh.spring_ai.service;

import dev.rishabh.spring_ai.tools.DateTimeTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ToolCallingService {
    private final ChatClient openAiChatCLient;

    public String generateAiResponseWithTools(String prompt){
        return openAiChatCLient.prompt(prompt)
                .tools(new DateTimeTools())
                .call()
                .content();
    }
}
