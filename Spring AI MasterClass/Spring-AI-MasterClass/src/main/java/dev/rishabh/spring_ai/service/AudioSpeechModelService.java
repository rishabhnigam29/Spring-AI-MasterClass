package dev.rishabh.spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AudioSpeechModelService {

    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;
    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    public String transcribe(Resource audioResource) {
        AudioTranscriptionPrompt transcriptionPrompt = new AudioTranscriptionPrompt(audioResource,
                OpenAiAudioTranscriptionOptions.builder()
                        .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                        .temperature(0f)
                        .language("en")
                        .build());

        AudioTranscriptionResponse transcriptionResponse = openAiAudioTranscriptionModel.call(transcriptionPrompt);
        return transcriptionResponse.getResult().getOutput();
    }

    public byte[] tts(String text) {
        SpeechPrompt speechPrompt = new SpeechPrompt(text,
                OpenAiAudioSpeechOptions.builder()
                        .model(OpenAiAudioApi.TtsModel.TTS_1.value)
                        .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY.value)
                        .speed(1.0f)
                        .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                        .build());
        SpeechResponse speechResponse = openAiAudioSpeechModel.call(speechPrompt);
        return speechResponse.getResult().getOutput();
    }
}
