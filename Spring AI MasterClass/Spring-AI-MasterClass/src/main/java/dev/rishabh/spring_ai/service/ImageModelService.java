package dev.rishabh.spring_ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageModelService {

    @Qualifier("openAiImageModel")
    private final ImageModel openAiImageModel;

    @Qualifier("stabilityAiImageModel")
    private final ImageModel stabilityAiImageModel;

    public byte[] generateImage(String prompt, String model){
        log.info("Generating image with model: {} for prompt: {}", model, prompt);
        if ("openai".equalsIgnoreCase(model)) {
            return generateImageWithOpenAi(prompt);
        } else if ("stabilityai".equalsIgnoreCase(model)) {
            return generateImageWithStabilityAi(prompt);
        } else {
            throw new IllegalArgumentException("Unsupported model: " + model);
        }
    }

    public byte[] generateImageWithOpenAi(String prompt) {
        log.info("Generating image with OpenAI for prompt: {}", prompt);
        OpenAiImageOptions openAiImageOptions = OpenAiImageOptions.builder()
                .quality("hd")
                .style("natural")
                .N(1)
                .height(1024)
                .width(1024)
                .responseFormat("b64_json")
                .build();
        return generateImage(openAiImageModel, openAiImageOptions, prompt);
    }

    public byte[] generateImageWithStabilityAi(String prompt) {
        log.info("Generating image with StabilityAI for prompt: {}", prompt);
        StabilityAiImageOptions stabilityAiImageOptions = StabilityAiImageOptions.builder()
                .N(1)
                .height(1024)
                .width(1024)
                .build();
        return generateImage(stabilityAiImageModel, stabilityAiImageOptions, prompt);
    }

    public byte[] generateImage(ImageModel imageModel,
                                ImageOptions imageOptions,
                                String prompt) {
        ImageResponse imageResponse = imageModel.call(
                new ImagePrompt(prompt, imageOptions));

        String b64Json = imageResponse.getResult().getOutput().getB64Json();
        return Base64.getDecoder().decode(b64Json);
    }
}
