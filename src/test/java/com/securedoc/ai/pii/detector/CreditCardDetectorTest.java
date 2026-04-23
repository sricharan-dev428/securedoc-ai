package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
public class CreditCardDetectorTest {

    private final CreditCardDetector detector = new CreditCardDetector();
    @Test
    public void detectSingleCard() {
        String text = "My card is 4532 1234 5678 9010";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).hasSize(1);
    }

    @Test
    public void detectMultipleCards() {
        String text = "Cards: 4532123456789010 and 4111111111111111";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).hasSize(2);
    }

    @Test
    void shouldReturnEmptyWhenNoCardDetected() {
        String text = "No card here";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).isEmpty();
    }
}
