package com.securedoc.ai.document.entity;

import com.securedoc.ai.auth.entity.User;
import com.securedoc.ai.pii.entity.PiiDetectionRecord;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "documents")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "doc_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName;

    @Column(name = "stored_file_name", nullable = false, length = 512)
    private String storedFileName;

    @Column(name = "file_path", nullable = false, length = 512)
    private String filePath;

    @Column(name = "file_type", nullable = false, length = 100)
    private String fileType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PiiDetectionRecord> piiDetections;

    @Column(name = "sanitized_content", columnDefinition = "TEXT")
    private String sanitizedContent;

    @Column(name = "redaction_strategy", length = 50)
    private String redactionStrategy;
}