package com.securedoc.ai.document.service;

import com.securedoc.ai.auth.entity.User;
import com.securedoc.ai.document.dto.DocumentUploadResponse;
import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String storedFileName = UUID.randomUUID().toString() + extension;

        Path uploadPath = Path.of(uploadDirectory);
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(storedFileName);
        Files.copy(file.getInputStream(), filePath);

        Document document = Document.builder()
                .originalName(originalFilename)
                .storedFileName(storedFileName)
                .filePath(filePath.toString())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .user(user)
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
}