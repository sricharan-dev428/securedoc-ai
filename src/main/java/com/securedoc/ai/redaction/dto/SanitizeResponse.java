package com.securedoc.ai.redaction.dto;

import lombok.*;

import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SanitizeResponse {
    private UUID documentId;
    private String sanitizedContent;
    private String strategyUsed;
    private int piiItemsRedacted;
}
