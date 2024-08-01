package dev.riqui.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ricardoquiroga on 01-08-24
 */
@Service
@Slf4j
public class ChatService {

    private final FileStorageService fileStorageService;
    private final ChatClient chatClient;

    public ChatService(FileStorageService fileStorageService, ChatClient.Builder chatClient) {

        this.fileStorageService = fileStorageService;
        this.chatClient = chatClient.build();
    }

    public String generateResponse(String question, MultipartFile file) {
        String filePath = "";
        if (!file.isEmpty()) {
            filePath = fileStorageService.getStoredFilePath(file);
        }
        log.info("filePath: {}", filePath);
        log.info("question {}", question);
        String input = question + " " + filePath;
        log.info("input: {}", input);
        return chatClient.prompt()
                .user(input)
                .call()
                .content();

    }
}
