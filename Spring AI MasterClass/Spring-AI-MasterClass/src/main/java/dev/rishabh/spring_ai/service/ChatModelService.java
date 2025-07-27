package dev.rishabh.spring_ai.service;

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

@Service
@RequiredArgsConstructor
public class ChatModelService {
    private final ChatModel chatModel;

    public String generateText(String prompt){
        return chatModel.call(prompt);
    }

    public String generateTextWithMultimodalImageResource(String prompt, Resource imageResource){

        return  generateTextWithMultimodalResource(prompt,
                imageResource,
                MimeTypeUtils.IMAGE_PNG,
                OpenAiApi.ChatModel.GPT_4_O.value);
    }

    public String generateTextWithMultimodalAudioResource(String prompt, Resource audioResource){

        return  generateTextWithMultimodalResource(prompt,
                audioResource,
                MimeTypeUtils.parseMimeType("audio/mp3"),
                OpenAiApi.ChatModel.GPT_4_O_AUDIO_PREVIEW.getName());
    }

    private String generateTextWithMultimodalResource(String prompt,
                                                     Resource resource,
                                                     MimeType mimeType,
                                                     String modelName) {
        UserMessage userMessage = UserMessage.builder().text(prompt)
                .media(new Media(mimeType, resource))
                .build();

        ChatResponse chatResponse = chatModel.call(new Prompt(userMessage,
                OpenAiChatOptions.builder()
                        .model(modelName)
                        .build()));

        return chatResponse.getResult().getOutput().getText();

    }
}
