package dev.rishabh.spring_ai.config;

import dev.rishabh.spring_ai.constants.AiModels;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class ModelConfig {

    @Bean
    public ChatClient openAiChatCLient(OpenAiChatModel chatModel){
        return ChatClient.create(chatModel);
    }

    @Bean
    public Map<AiModels, ChatModel> chatModels(
            ChatModel openAiChatModel,
            ChatModel ollamaChatModel
    ){
        return Map.of(
                AiModels.OPENAI, openAiChatModel,
                AiModels.OLLAMA, ollamaChatModel
        );
    }
}
