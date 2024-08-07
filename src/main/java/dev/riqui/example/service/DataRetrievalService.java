package dev.riqui.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ricardoquiroga on 04-08-24
 */
@Service
@RequiredArgsConstructor
public class DataRetrievalService {

    private final VectorStore vectorStore;

    public List<Document> searchData(String query) {
        return vectorStore.similaritySearch(query);
    }
}
