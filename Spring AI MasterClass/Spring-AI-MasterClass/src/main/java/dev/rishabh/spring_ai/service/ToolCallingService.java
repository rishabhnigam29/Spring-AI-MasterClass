package dev.rishabh.spring_ai.service;

import dev.rishabh.spring_ai.tools.DateTimeTools;
import dev.rishabh.spring_ai.tools.WeatherTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.support.ToolCallbacks;
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

    public String generateAiResponseWithCustomToolExecution(String userInput){
        DefaultToolCallingManager toolCallingManager =
                ToolCallingManager.builder().build();

        ToolCallingChatOptions toolCallingChatOptions = ToolCallingChatOptions.builder()
                .toolCallbacks(ToolCallbacks.from(new DateTimeTools(), weatherTools))
                .internalToolExecutionEnabled(false)
                .build();

        Prompt prompt = new Prompt(userInput, toolCallingChatOptions);

        ChatResponse chatResponse = openAiChatCLient
                .prompt(prompt).call().chatResponse();

        while (chatResponse.hasToolCalls()){
            ToolExecutionResult toolExecutionResult = toolCallingManager
                    .executeToolCalls(prompt, chatResponse);

            prompt = new Prompt(toolExecutionResult.conversationHistory(),
                    toolCallingChatOptions);

            chatResponse = openAiChatCLient.prompt(prompt).call().chatResponse();
        }

        return chatResponse.getResult().getOutput().getText();

    }
}
