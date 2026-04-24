package com.securedoc.ai.redaction.strategy;

import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RedactStrategy implements RedactionStrategy{
    @Override
    public String sanitize(String text, List<PiiDetectionResult> detections, Document document) {
        Map<PiiType, Integer> counters = new HashMap<>();

        for(PiiDetectionResult detection : detections){
            int count= counters.getOrDefault(detection.getPiiType(), 0)+1;
            counters.put(detection.getPiiType(), count);
            String placeholder="[" + detection.getPiiType().name() + "_" + count + "]";
            text = text.replace(detection.getPiiValue(), placeholder);
        }
        return text;
    }



}
