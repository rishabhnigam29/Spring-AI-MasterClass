package dev.rishabh.spring_ai.controller;

import dev.rishabh.spring_ai.constants.AiModels;
import dev.rishabh.spring_ai.service.AudioSpeechModelService;
import dev.rishabh.spring_ai.service.ChatModelService;
import dev.rishabh.spring_ai.service.ImageModelService;
import dev.rishabh.spring_ai.service.ModerationModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AiModelController {

    private final ChatModelService chatModelService;
    private final ImageModelService imageModelService;
    private final AudioSpeechModelService audioSpeechModelService;
    private final ModerationModelService moderationModelService;

    @GetMapping("/generate-text-with-ai")
    public Flux<String> generateAiText(@RequestParam AiModels aiModels, @RequestParam String prompt) {
        return chatModelService.generateText(aiModels, prompt);
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

    @PostMapping("/generate-audio-transcription")
    public ResponseEntity<String> transcribeAudio(@RequestParam("file") MultipartFile audioResource) {
        Resource resource = audioResource.getResource();
        String transcription = audioSpeechModelService.transcribe(audioResource);
        return ResponseEntity.ok(transcription);
    }

    @PostMapping("/moderate-text")
    public ResponseEntity<?> moderateText(@RequestParam String model,
                                          @RequestParam String userText) {
        return ResponseEntity.ok(moderationModelService.moderateText(model, userText));
    }

    @PostMapping("/generate-text-with-multimodal")
    public ResponseEntity<?> generateTextWithMultimodalImage(@RequestParam String prompt,
                                                             @RequestParam("file") MultipartFile file,
                                                             @RequestParam String format,
                                                             @RequestParam(required = false) AiModels aiModels) {
        Resource imageResource = file.getResource();
        if ("image".equalsIgnoreCase(format)){
            return ResponseEntity.ok(chatModelService.generateTextWithMultimodalImageResource(aiModels, prompt, imageResource));
        } else if ("audio".equalsIgnoreCase(format)) {
            return ResponseEntity.ok(chatModelService.generateTextWithMultimodalAudioResource(prompt, imageResource));
        } else {
            return ResponseEntity.badRequest().body("Unsupported format. Use 'image' or 'audio'.");
        }
    }
}
