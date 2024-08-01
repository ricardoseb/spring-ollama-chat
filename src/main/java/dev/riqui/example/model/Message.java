package dev.riqui.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ricardoquiroga on 01-08-24
 */
@AllArgsConstructor
@Data
public class Message {
    private String question;
    private String response;

}
