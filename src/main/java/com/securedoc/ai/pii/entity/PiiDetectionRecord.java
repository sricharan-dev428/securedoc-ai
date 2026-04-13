package com.securedoc.ai.pii.entity;

import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.pii.model.PiiType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pii_detections")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PiiDetectionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Enumerated(EnumType.STRING)
    @Column(name = "pii_type", nullable = false, length = 50)
    private PiiType piiType;

    @Column(name = "pii_value", length = 500)
    private String piiValue;

    @Column(name = "start_pos", nullable = false)
    private int start;

    @Column(name = "end_pos", nullable = false)
    private int end;

    @Column(name = "confidence", nullable = false)
    private double confidence;

    @CreationTimestamp
    @Column(name = "detected_at", nullable = false, updatable = false)
    private LocalDateTime detectedAt;
}