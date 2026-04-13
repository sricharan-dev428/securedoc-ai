package com.securedoc.ai.pii.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PiiDetectionResult {
    private PiiType piiType;
    private String piiValue;
    private int start;
    private int end;
    private double confidence;
}
