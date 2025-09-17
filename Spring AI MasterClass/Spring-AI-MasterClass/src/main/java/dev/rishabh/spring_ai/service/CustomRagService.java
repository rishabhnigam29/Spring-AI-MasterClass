package dev.rishabh.spring_ai.service;

import dev.rishabh.spring_ai.constants.AiModels;
import dev.rishabh.spring_ai.utils.CosineSimilarityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomRagService {
    private final EmbeddingModelService embeddingModelService;
    private final ChatModelService chatModelService;
    private final Map<String, float[]> chunkStore = new ConcurrentHashMap<>();

    public void addChunks(List<String> chunks){
        for(String chunk: chunks){
            chunkStore.put(chunk, embeddingModelService.getEmbeddings(chunk));
        }
        log.info("Stored embedding for all the chunks");
    }

    private String getMostRelevantChunk(String query){
        String mostRelevantChunk=null;
        double maximumSimilarity= -1;
        float[] embeddingsForQuery = embeddingModelService.getEmbeddings(query);

        for (Map.Entry<String, float[]> entry: chunkStore.entrySet()){
            double cosineSimilarity = CosineSimilarityUtils
                    .cosineSimilarity(embeddingsForQuery, entry.getValue());

            if(cosineSimilarity>maximumSimilarity){
                mostRelevantChunk = entry.getKey();
                maximumSimilarity = cosineSimilarity;
            }
        }

        return mostRelevantChunk;
    }

    private Flux<String> generateResponse(String query, String mostRelevantChunk){
        String prompt = String.format("Using the following context %s," +
                " answer this question %s", mostRelevantChunk, query);
        return chatModelService.generateText(AiModels.OLLAMA, prompt);
    }

    public Flux<String> customRag(String query){
        String mostRelevantChunk = getMostRelevantChunk(query);
        if (null == mostRelevantChunk){
            return Flux.just("No Relevant Information Found");
        }
        return generateResponse(query, mostRelevantChunk);
    }
}
