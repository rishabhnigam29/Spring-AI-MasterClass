package dev.rishabh.spring_ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.moderation.ModerationModel;
import org.springframework.ai.moderation.ModerationPrompt;
import org.springframework.ai.moderation.ModerationResponse;
import org.springframework.ai.moderation.ModerationResult;
import org.springframework.ai.openai.OpenAiModerationOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModerationModelService {

    private final ModerationModel moderationModel;

    public List<ModerationResult> moderateText(String text){
        ModerationResponse moderationResponse = moderationModel.call(new ModerationPrompt(
                text,
                OpenAiModerationOptions.builder()
                        .model("text-moderation-latest")
                        .build()));

        return moderationResponse.getResult().getOutput().getResults();
    }
}
