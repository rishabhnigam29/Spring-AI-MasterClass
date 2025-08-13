package dev.rishabh.spring_ai.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AiModels {
    OPENAI("openai"),
    OLLAMA("ollama");

    private final String modelName;
}
