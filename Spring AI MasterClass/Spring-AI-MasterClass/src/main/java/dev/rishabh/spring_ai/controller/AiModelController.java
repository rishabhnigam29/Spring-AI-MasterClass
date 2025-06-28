package dev.rishabh.spring_ai.controller;

import dev.rishabh.spring_ai.service.AudioSpeechModelService;
import dev.rishabh.spring_ai.service.ChatModelService;
import dev.rishabh.spring_ai.service.ImageModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AiModelController {

    private final ChatModelService chatModelService;
    private final ImageModelService imageModelService;
    private final AudioSpeechModelService audioSpeechModelService;

    @GetMapping("/generate-text-with-ai")
    public String generateAiText(@RequestParam String prompt) {
        return chatModelService.generateText(prompt);
    }

    @PostMapping(value = "/generate-image-with-ai", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> generateAiImage(@RequestParam String prompt,
                                             @RequestParam String model) {
        return ResponseEntity.ok(imageModelService.generateImage(prompt,model));
    }

    @PostMapping(value = "/generate-audio-with-ai", produces = "audio/mpeg")
    public ResponseEntity<byte[]> generateAudioWithAi(@RequestParam String text) {
        byte[] audioData = audioSpeechModelService.tts(text);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mpeg"))
                .body(audioData);
    }











}
