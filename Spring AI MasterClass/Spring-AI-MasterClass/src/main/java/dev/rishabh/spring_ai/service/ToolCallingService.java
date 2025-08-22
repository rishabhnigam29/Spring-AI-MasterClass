package dev.rishabh.spring_ai.service;

import dev.rishabh.spring_ai.tools.DateTimeTools;
import dev.rishabh.spring_ai.tools.WeatherTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ToolCallingService {
    private final ChatClient openAiChatCLient;
    private final WeatherTools weatherTools;

    public String generateAiResponseWithTools(String prompt){
        return openAiChatCLient.prompt(prompt)
                .tools(new DateTimeTools(), weatherTools)
                .call()
                .content();
    }
}
