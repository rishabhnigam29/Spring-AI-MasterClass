package dev.rishabh.spring_ai.controller;

import dev.rishabh.spring_ai.service.ChatModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AiModelController {

    private final ChatModelService chatModelService;

    @GetMapping("/generate-text-with-ai")
    public String generateAiText(@RequestParam String prompt) {
        return chatModelService.generateText(prompt);
    }
}
