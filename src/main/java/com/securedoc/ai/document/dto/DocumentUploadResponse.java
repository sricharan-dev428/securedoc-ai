package com.securedoc.ai.document.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
import com.securedoc.ai.pii.model.PiiDetectionResult;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentUploadResponse {


    private UUID id;
    private String originalName;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadedAt;
    private int piiDetectionsCount;
    private List<PiiDetectionResult> detections;

}
