package dev.riqui.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author ricardoquiroga on 04-08-24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DataLoaderService {


    private final VectorStore vectorStore;

    private final FileStorageService fileStorageService;

    public void load(MultipartFile file) {
        try {
            String filePath = fileStorageService.getStoredFilePath(file);
            log.info("Processing PDF file: {}", filePath);

            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
                    new File(filePath).toURI().toString(),
                    PdfDocumentReaderConfig.builder()
                            .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                    .withNumberOfBottomTextLinesToDelete(3)
                                    .withNumberOfTopPagesToSkipBeforeDelete(1)
                                    .build())
                            .withPagesPerDocument(1)
                            .build());

            var tokenTextSplitter = new TokenTextSplitter();
            List<Document> documents = tokenTextSplitter.apply(pdfReader.get());
            log.info("Extracted {} documents from PDF", documents.size());

            this.vectorStore.add(documents);
            log.info("Documents added to vector store successfully");
        } catch (Exception e) {
            log.error("Error processing PDF file", e);
            throw new RuntimeException("Failed to process PDF file", e);
        }
    }
}


