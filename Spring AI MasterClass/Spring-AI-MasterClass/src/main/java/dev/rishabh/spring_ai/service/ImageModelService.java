package dev.rishabh.spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImageModelService {

    private final ImageModel imageModel;

    public byte[] generateImage(String prompt) {
        ImageResponse imageResponse = imageModel.call(
                new ImagePrompt(prompt,
                        OpenAiImageOptions.builder()
                                .quality("hd")
                                .N(1)
                                .height(1024)
                                .width(1024)
                                .responseFormat("b64_json")
                                .build()));

        String b64Json = imageResponse.getResult().getOutput().getB64Json();
        return Base64.getDecoder().decode(b64Json);
    }
}
