package dev.rishabh.spring_ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmbeddingModelService {
    private final EmbeddingModel embeddingModel;

    public float[] getEmbeddings(String text){
        return embeddingModel.embed(text);
    }

}
