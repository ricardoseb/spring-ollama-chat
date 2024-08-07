package dev.riqui.example.controller;

import dev.riqui.example.model.Message;
import dev.riqui.example.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author ricardoquiroga on 01-08-24
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/ai")
    public String ask(@RequestParam("question") String question,
                      @RequestParam("file") MultipartFile file,
                      Model model) {
        String response = "Unsupported file";
        if (file.isEmpty()) {
            log.info("File is empty");
            response = chatService.chatWithoutUsingVectorDB(question);
            model.addAttribute("message", new Message(question, response));
            return "result";

        } else if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".pdf")) {
            log.info("File is PDF");
            response = chatService.chatUsingVectorDB(question, file);
            model.addAttribute("message", new Message(question, response));
            return "result";
        }
        log.info("File is not PDF");
        model.addAttribute("message", new Message(question, response));
        return "result";

    }
}
