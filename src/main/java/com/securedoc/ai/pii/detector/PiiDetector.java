package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;

import java.util.List;

public interface PiiDetector {
    List<PiiDetectionResult> detect(String text);
}
