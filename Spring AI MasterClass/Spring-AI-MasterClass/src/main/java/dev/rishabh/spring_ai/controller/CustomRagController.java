package dev.rishabh.spring_ai.controller;

import dev.rishabh.spring_ai.service.CustomRagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rag")
@Slf4j
public class CustomRagController {

    private final CustomRagService customRagService;

    @PostMapping("/chunks")
    public ResponseEntity<?> addChunks(@RequestBody List<String> chunks){
        customRagService.addChunks(chunks);
        return ResponseEntity.ok("Chunks added successfully");
    }

    @GetMapping("/query")
    public Flux<String> query(@RequestParam String query){
        return customRagService.customRag(query);
    }
}
