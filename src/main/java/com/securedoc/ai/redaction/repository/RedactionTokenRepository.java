package com.securedoc.ai.redaction.repository;

import com.securedoc.ai.document.entity.Document;
import com.securedoc.ai.redaction.entity.RedactionToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RedactionTokenRepository extends JpaRepository<RedactionToken, UUID> {
    List<RedactionToken> findByDocument(Document document);
}
