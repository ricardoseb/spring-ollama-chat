package dev.riqui.example.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ricardoquiroga on 07-08-24
 */
@Getter
@AllArgsConstructor
public enum ExtensionFile {
    PDF(".pdf");

    private final String value;
}
