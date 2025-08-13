package dev.rishabh.spring_ai.service;

import dev.rishabh.spring_ai.constants.AiModels;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatModelService {
    private final Map<AiModels, ChatModel> chatModels;

    public Flux<String> generateText(AiModels aiModels, String prompt){
        return chatModels.get(aiModels).stream(prompt);
    }

    public String generateTextWithMultimodalImageResource(AiModels aiModels,
                                                          String prompt,
                                                          Resource imageResource){

        return  generateTextWithMultimodalResource(aiModels, prompt,
                imageResource,
                MimeTypeUtils.IMAGE_PNG,
                AiModels.OPENAI == aiModels ?
                        OpenAiApi.ChatModel.GPT_4_O.value : "gemma3:4b");
    }

    public String generateTextWithMultimodalAudioResource(String prompt, Resource audioResource){

        return  generateTextWithMultimodalResource(AiModels.OPENAI, prompt,
                audioResource,
                MimeTypeUtils.parseMimeType("audio/mp3"),
                OpenAiApi.ChatModel.GPT_4_O_AUDIO_PREVIEW.getName());
    }

    private String generateTextWithMultimodalResource(AiModels aiModels,
                                                      String prompt,
                                                     Resource resource,
                                                     MimeType mimeType,
                                                     String modelName) {
        UserMessage userMessage = UserMessage.builder().text(prompt)
                .media(new Media(mimeType, resource))
                .build();

        ChatResponse chatResponse = chatModels.get(aiModels).call(new Prompt(userMessage,
                OpenAiChatOptions.builder()
                        .model(modelName)
                        .build()));

        return chatResponse.getResult().getOutput().getText();

    }
}
