package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
public class IpAddressDetectorTest {
    private final IpAddressDetector detector = new IpAddressDetector();
    @Test
    public void detectSingleIp() {
        String text = "Server IP is 192.168.1.1";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getPiiValue()).isEqualTo("192.168.1.1");
        assertThat(results.get(0).getPiiType()).isEqualTo(PiiType.IP_ADDRESS);
    }

    @Test
    public void detectMultipleIps() {
        String text = "IPs: 192.168.1.1 and 10.0.0.1";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).hasSize(2);
    }

    @Test
    void shouldReturnEmptyWhenNoIpDetected() {
        String text = "No IP here";
        List<PiiDetectionResult> results = detector.detect(text);

        assertThat(results).isEmpty();
    }
}
