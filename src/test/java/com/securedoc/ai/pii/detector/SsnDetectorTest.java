package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SsnDetectorTest {
    private final SsnDetector detector = new SsnDetector();

    @Test
    public void detectSingleSsn() {
        String text = "My SSN is 123-45-6789";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getPiiValue()).isEqualTo("123-45-6789");
        assertThat(results.get(0).getPiiType()).isEqualTo(PiiType.SSN);
    }

    @Test
    public void detectMultipleSsns() {
        String text = "SSNs: 123-45-6789 and 987-65-4321";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).hasSize(2);
    }

    @Test
    void shouldReturnEmptyWhenNoSsnDetected() {
        String text = "No SSN here";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).isEmpty();
    }
}
