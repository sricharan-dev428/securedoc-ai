package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class EmailDetectorTest {
    private final EmailDetector detector = new EmailDetector();

    @Test
    void shouldDetectSingleEmail() {
        String text = "Contact me at john@example.com for details";

        // Act
        List<PiiDetectionResult> results = detector.detect(text);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getPiiValue()).isEqualTo("john@example.com");
        assertThat(results.get(0).getPiiType()).isEqualTo(PiiType.EMAIL);

    }

    @Test
    void shouldDetectMultipleEmails() {
        // Arrange
        String text = "Email john@example.com or jane@company.org";

        // Act
        List<PiiDetectionResult> results = detector.detect(text);

        // Assert
        assertThat(results).hasSize(2);
    }

    @Test
    void shouldReturnEmptyWhenNoEmail() {
        // Arrange
        String text = "There is no email here";

        // Act
        List<PiiDetectionResult> results = detector.detect(text);

        // Assert
        assertThat(results).isEmpty();
    }
}
