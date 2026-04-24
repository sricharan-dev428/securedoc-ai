package com.securedoc.ai.redaction.controller;

import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.document.repository.DocumentRepository;
import com.securedoc.ai.pii.repository.PiiDetectionRepository;
import com.securedoc.ai.redaction.dto.SanitizeResponse;
import com.securedoc.ai.redaction.service.RedactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class SanitizeController {

    private final RedactionService redactionService;
    private final DocumentRepository documentRepository;
    private final PiiDetectionRepository piiDetectionRepository;

    @PostMapping("/{id}/sanitize")
    public ResponseEntity<SanitizeResponse> sanitize(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "REDACT") String strategy) {

        // Find document or throw 404
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        // Run sanitization
        Document sanitized = redactionService.sanitize(document, strategy);

        // Count PII items redacted
        int count = piiDetectionRepository.findByDocument(sanitized).size();

        // Return response
        return ResponseEntity.ok(SanitizeResponse.builder()
                .documentId(sanitized.getId())
                .sanitizedContent(sanitized.getSanitizedContent())
                .strategyUsed(strategy)
                .piiItemsRedacted(count)
                .build());
    }
}