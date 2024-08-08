package dev.riqui.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
                    fileStorageService.getStoredResource(file),
                    PdfDocumentReaderConfig.builder()
                            .withPageTopMargin(0)
                            .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                    .withNumberOfBottomTextLinesToDelete(3)
                                    .withNumberOfTopPagesToSkipBeforeDelete(1)
                                    .build())
                            .withPagesPerDocument(1)
                            .build());

            var tokenTextSplitter = new TokenTextSplitter();
            this.vectorStore.accept(tokenTextSplitter.apply(pdfReader.get()));
            log.info("Documents added to vector store successfully");
        } catch (Exception e) {
            log.error("Error processing PDF file", e);
            throw new RuntimeException("Failed to process PDF file", e);
        }
    }
}


