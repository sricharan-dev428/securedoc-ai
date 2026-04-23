package com.securedoc.ai.pii.detector;


import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
public class PhoneDetectorTest {
    private final PhoneDetector detector = new PhoneDetector();

    @Test
    public void detectSinglePhoneNumber() {
        String text="My phone is 555-123-4567";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getPiiValue()).isEqualTo("555-123-4567");
        assertThat(results.get(0).getPiiType()).isEqualTo(PiiType.PHONE);

    }

    @Test
    public void detectMultiplePhoneNumbers() {
        String text="Call 555-123-4567 or 800-987-6543";
        List<PiiDetectionResult> results = detector.detect(text);
        assertThat(results).hasSize(2);
    }

    @Test
    void shouldReturnEmptyWhenNoPhoneDetected() {
        String text="No phone number here";
        List<PiiDetectionResult> results = detector.detect(text);
        assertThat(results).isEmpty();
    }
}

