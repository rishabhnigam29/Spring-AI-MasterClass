package dev.rishabh.spring_ai.controller;

import dev.rishabh.spring_ai.service.ToolCallingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class AiToolCallingController {
    private final ToolCallingService toolCallingService;

    @GetMapping("/generate-ai-response-with-tools")
    public String generateAiResponseWithTools(@RequestParam String prompt) {
        log.info("Received prompt: {}", prompt);
        String response = toolCallingService.generateAiResponseWithTools(prompt);
        log.info("Generated AI response: {}", response);
        return response;
    }
}
