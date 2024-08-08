package dev.riqui.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.riqui.example.util.OllamaModel.LLAVA13;

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
    private final ChatModel chatModel;


    public ChatService(FileStorageService fileStorageService, ChatClient.Builder chatClient,
                       DataRetrievalService dataRetrievalService, DataLoaderService dataLoaderService, ChatModel chatModel) {
        this.fileStorageService = fileStorageService;
        this.chatClient = chatClient.build();
        this.dataRetrievalService = dataRetrievalService;
        this.dataLoaderService = dataLoaderService;
        this.chatModel = chatModel;
    }


    //Without Using Vector DB
    public String chatWithoutUsingVectorDB(String question, MultipartFile file) {
        UserMessage userMessage;
        MimeType mimeType = file.getOriginalFilename().endsWith(MimeTypeUtils.IMAGE_PNG.getSubtype()) ?
                MimeTypeUtils.IMAGE_PNG : MimeTypeUtils.IMAGE_JPEG;
        Resource resource = fileStorageService.getStoredResource(file);
        userMessage = new UserMessage(question,
                List.of(new Media(mimeType, resource)));
        ChatResponse response = chatModel.call(
                new Prompt(List.of(userMessage), OllamaOptions.create().withModel(LLAVA13.getValue()).withTemperature(0.2F)));
        return response.getResult().getOutput().getContent();
    }

    //Using Vector DB // RAG
    public String chatUsingVectorDB(String question, MultipartFile file) {
        dataLoaderService.load((file));
        return chatClient.prompt()
                .user(u -> u.text(assistantPrompt).params(createParams(question,
                        dataRetrievalService.searchData(question))))
                .call()
                .content();
    }

    public String chat(String question){
        return this.chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    private Map<String, Object> createParams(String question, List<Document> context) {
        Map<String, Object> params = new HashMap<>();
        params.put("question", question);
        params.put("context", context);
        return params;
    }
}
