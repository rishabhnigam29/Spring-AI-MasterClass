package dev.rishabh.spring_ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.moderation.ModerationModel;
import org.springframework.ai.moderation.ModerationPrompt;
import org.springframework.ai.moderation.ModerationResult;  
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModerationModelService {

    @Qualifier("openAiModerationModel")
    private final ModerationModel openAiModerationModel;

    @Qualifier("mistralAiModerationModel")
    private final ModerationModel mistralAiModerationModel;

    public List<ModerationResult> moderateText(String model, String text) {
        log.info("Moderating text with model: {} for text: {}", model, text);
        if ("openai".equalsIgnoreCase(model)) {
            return moderateText(openAiModerationModel, text);
        } else if ("mistralai".equalsIgnoreCase(model)) {
            return moderateText(mistralAiModerationModel, text);
        } else {
            throw new IllegalArgumentException("Unsupported model: " + model);
        }
    }


    public List<ModerationResult> moderateText(ModerationModel moderationModel,
                                               String text){
        return moderationModel
                .call(new ModerationPrompt(text))
                .getResult()
                .getOutput()
                .getResults();
    }
}
