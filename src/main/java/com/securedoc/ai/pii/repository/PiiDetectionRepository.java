package com.securedoc.ai.pii.repository;

import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.pii.entity.PiiDetectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PiiDetectionRepository extends JpaRepository<PiiDetectionRecord, UUID> {
    List<PiiDetectionRecord> findByDocument(Document document);




}
