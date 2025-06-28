package dev.rishabh.spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AudioSpeechModelService {

    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;

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
