package dev.rishabh.spring_ai.model;

import dev.rishabh.spring_ai.pojo.TranscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomAudioTranscribeModel {
    private final RestTemplate restTemplate;
    @Value("${whisper.api.url:http://localhost:8000/transcribe}")
    private String whisperApiUrl;

    public String transcribeAudio(MultipartFile multipartFile){
        try{
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", multipartFile.getResource());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<TranscriptionResponse> responseEntity = restTemplate
                    .postForEntity(whisperApiUrl, requestEntity, TranscriptionResponse.class);

            return Optional.of(responseEntity.getBody())
                    .map(TranscriptionResponse::transcription)
                    .map(TranscriptionResponse.Transcription::text)
                    .orElseThrow(() -> new RuntimeException("Transcription response is empty or malformed"));

        }catch (Exception e) {
            throw new RuntimeException("Error during audio transcription", e);
        }
    }
}
