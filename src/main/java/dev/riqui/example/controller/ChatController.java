package dev.riqui.example.controller;

import dev.riqui.example.model.Message;
import dev.riqui.example.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ricardoquiroga on 01-08-24
 */
@Controller
@RequiredArgsConstructor
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
        String response = chatService.generateResponse(question, file);
        model.addAttribute("message", new Message(question, response));
        return "result";
    }
}
