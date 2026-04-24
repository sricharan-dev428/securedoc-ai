package com.securedoc.ai.redaction.entity;

import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.pii.model.PiiType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "redaction_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedactionToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "token", nullable = false, unique = true, updatable = false)
    private String token;

    @Column(name = "original_value", nullable = false, length = 500)
    private String originalValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "pii_type", nullable = false, length = 50)
    private PiiType piiType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}