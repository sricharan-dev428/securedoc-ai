package com.securedoc.ai.document.repository;

import com.securedoc.ai.auth.entity.User;
import com.securedoc.ai.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

    List<Document> findByUserOrderByUploadedAtDesc(User user);
}
