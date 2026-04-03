package com.securedoc.ai.document.service;

import com.securedoc.ai.auth.entity.User;
import com.securedoc.ai.document.dto.DocumentUploadResponse;
import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Value("${app.upload.dir}")
    private String uploadDirectory;

    public DocumentUploadResponse uploadDocument(MultipartFile file, User user) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        validateFile(file);

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String storedFileName = UUID.randomUUID().toString() + extension;

        Path uploadPath = Path.of(uploadDirectory);
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(storedFileName);
        Files.copy(file.getInputStream(), filePath);

        String extractedText = extractText(file);

        Document document = Document.builder()
                .originalName(originalFilename)
                .storedFileName(storedFileName)
                .filePath(filePath.toString())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .user(user)
                .content(extractedText)
                .build();

        Document saved = documentRepository.save(document);

        return DocumentUploadResponse.builder()
                .id(saved.getId())
                .originalName(saved.getOriginalName())
                .fileType(saved.getFileType())
                .fileSize(saved.getFileSize())
                .uploadedAt(saved.getUploadedAt())
                .build();
    }
    private void validateFile(MultipartFile file) throws IllegalArgumentException{
        String fileType=file.getContentType();
        List<String> allowedTypes = Arrays.asList(
                "application/pdf",
                "text/plain",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );
        if (fileType == null || !allowedTypes.contains(fileType)) {
            throw new IllegalArgumentException(
                    "File type not allowed. Accepted: PDF, TXT, DOCX"
            );
        }
    }

    private String extractText(MultipartFile file) throws IOException {
        String fileType = file.getContentType();
        if (fileType.equals("application/pdf")) {
            try (PDDocument document = Loader.loadPDF(file.getBytes())) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        }
        if (fileType.equals("text/plain")) {
            return new String(file.getBytes());
        }
        if (fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            try (XWPFDocument document = new XWPFDocument(file.getInputStream());
                 XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
                return extractor.getText();
            }
        }
        return "";
    }


}