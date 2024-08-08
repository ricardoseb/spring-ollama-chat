package dev.riqui.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author ricardoquiroga on 01-08-24
 */
@Service
@Slf4j
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    public Resource getStoredResource(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            // ruta relativa
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return new FileSystemResource(filePath.toFile());
        } catch (IOException ex) {
            log.error("Could not store file. Error: {}", ex.getMessage());
            throw new RuntimeException("Could not store file. Error: " + ex.getMessage());
        }
    }

}
