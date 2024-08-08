package dev.riqui.example.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ricardoquiroga on 07-08-24
 */
@Getter
@AllArgsConstructor
public enum OllamaModel {

    LLAVA13("llava:13b"),
    LLAMA3("llama3.1");

    private final String value;
}
