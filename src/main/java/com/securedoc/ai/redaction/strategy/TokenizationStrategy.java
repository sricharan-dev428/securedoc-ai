package com.securedoc.ai.redaction.strategy;

import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.redaction.entity.RedactionToken;
import com.securedoc.ai.redaction.repository.RedactionTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenizationStrategy implements RedactionStrategy {
    private final RedactionTokenRepository redactionTokenRepository;

    @Override
    public String sanitize(String text, List<PiiDetectionResult> detections,Document document) {
        for(PiiDetectionResult detection:detections){
            String token="TOKEN_"+ UUID.randomUUID().toString().substring(0,8);
            text=text.replace(detection.getPiiValue(),token);
            redactionTokenRepository.save(
                    RedactionToken.builder()
                            .document(document)
                            .token(token)
                            .originalValue(detection.getPiiValue())
                            .piiType(detection.getPiiType())
                            .build()
            );

        }
        return  text;
    }
}
