package com.securedoc.ai.redaction.service;

import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.document.repository.DocumentRepository;
import com.securedoc.ai.pii.entity.PiiDetectionRecord;
import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.repository.PiiDetectionRepository;
import com.securedoc.ai.redaction.repository.RedactionTokenRepository;
import com.securedoc.ai.redaction.strategy.RedactStrategy;
import com.securedoc.ai.redaction.strategy.RedactionStrategy;
import com.securedoc.ai.redaction.strategy.TokenizationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedactionService {
    private final RedactStrategy redactStrategy;
    private final TokenizationStrategy tokenizationStrategy;
    private final DocumentRepository documentRepository;
    private final PiiDetectionRepository piiDetectionRepository;

    public Document sanitize(Document document, String strategyName)
    {
        List<PiiDetectionResult> piiDetectionRecord=piiDetectionRepository.findByDocument(document)
                .stream()
                .map(record -> PiiDetectionResult.builder()
                        .piiType(record.getPiiType())
                        .piiValue(record.getPiiValue())
                        .start(record.getStart())
                        .end(record.getEnd())
                        .confidence(record.getConfidence())
                        .build())
                .toList();


        RedactionStrategy strategy="TOKENIZE".equals(strategyName)?tokenizationStrategy:redactStrategy;
        String sanitizedText=strategy.sanitize(document.getContent(), piiDetectionRecord, document);


        document.setSanitizedContent(sanitizedText);
        document.setRedactionStrategy(strategyName);


        return documentRepository.save(document);
    }


}
