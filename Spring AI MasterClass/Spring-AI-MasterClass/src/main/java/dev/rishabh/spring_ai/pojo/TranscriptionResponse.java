package dev.rishabh.spring_ai.pojo;

public record TranscriptionResponse(Transcription transcription) {
    public record Transcription(String text) {
    }
}
