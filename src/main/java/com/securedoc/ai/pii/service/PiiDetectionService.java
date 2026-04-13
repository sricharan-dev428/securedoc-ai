package com.securedoc.ai.pii.service;

import com.securedoc.ai.pii.detector.PiiDetector;
import com.securedoc.ai.pii.model.PiiDetectionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PiiDetectionService {

    private final List<PiiDetector> detectors;

    public List<PiiDetectionResult> detectPii(String text) {
        List<PiiDetectionResult> results = new ArrayList<>();
        for (PiiDetector detector : detectors) {
            results.addAll(detector.detect(text));
        }
        return results;
    }
}