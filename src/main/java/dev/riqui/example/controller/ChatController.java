package dev.riqui.example.controller;

import dev.riqui.example.model.Message;
import dev.riqui.example.service.ChatService;
import dev.riqui.example.util.ExtensionFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        String response;
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.isEmpty()) {
            if (originalFilename.endsWith(MimeTypeUtils.IMAGE_JPEG.getSubtype()) || originalFilename.endsWith(MimeTypeUtils.IMAGE_PNG.getSubtype())) {
                log.info("The uploaded file is a image");
                response = chatService.chatWithoutUsingVectorDB(question, file);
                model.addAttribute("message", new Message(question, response));
                return "result";
            } else if (originalFilename.endsWith(ExtensionFile.PDF.getValue())) {
                log.info("The uploaded file is a PDF");
                response = chatService.chatUsingVectorDB(question, file);
                model.addAttribute("message", new Message(question, response));
                return "result";
            } else {
                response = "Unsupported file, please try again with a valid extension file like .pdf, .jpeg or .png";
                model.addAttribute("message", new Message(question, response));
                return "result";
            }
        }
        response = chatService.chat(question);
        model.addAttribute("message", new Message(question, response));
        return "result";

    }
}
