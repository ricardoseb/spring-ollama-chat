package dev.riqui.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ricardoquiroga on 01-08-24
 */
@Service
@Slf4j
public class ChatService {

    @Value("classpath:/prompts/assistant.st")
    private Resource assistantPrompt;

    private final FileStorageService fileStorageService;
    private final ChatClient chatClient;
    private final DataRetrievalService dataRetrievalService;
    private final DataLoaderService dataLoaderService;


    public ChatService(FileStorageService fileStorageService, ChatClient.Builder chatClient,
                       DataRetrievalService dataRetrievalService, DataLoaderService dataLoaderService) {
        this.fileStorageService = fileStorageService;
        this.chatClient = chatClient.build();
        this.dataRetrievalService = dataRetrievalService;
        this.dataLoaderService = dataLoaderService;
    }


    //Without Using Vector DB
    public String chatWithoutUsingVectorDB(String question) {
        log.info("input: {}", question);
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    //Using Vector DB
    public String chatUsingVectorDB(String question, MultipartFile file) {
        dataLoaderService.load((file));
        return chatClient.prompt()
                .user(u -> u.text(assistantPrompt).params(createParams(question,
                        dataRetrievalService.searchData(question))))
                .call()
                .content();
    }

    private Map<String, Object> createParams(String question, List<Document> context) {
        Map<String, Object> params = new HashMap<>();
        params.put("question", question);
        params.put("context", context);
        return params;
    }

    private String getFilePath(MultipartFile file) {
        return fileStorageService.getStoredFilePath(file);
    }

}
