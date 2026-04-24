package com.securedoc.ai.redaction.strategy;

import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.pii.model.PiiDetectionResult;
import java.util.List;

public interface RedactionStrategy {
    String sanitize(String text, List<PiiDetectionResult> detections, Document document);
}
