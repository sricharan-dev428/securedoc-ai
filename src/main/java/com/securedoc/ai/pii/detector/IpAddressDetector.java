package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class IpAddressDetector implements PiiDetector {

    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile(
            "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b"
    );

    @Override
    public List<PiiDetectionResult> detect(String text) {
        List<PiiDetectionResult> results = new ArrayList<>();
        Matcher matcher = IP_ADDRESS_PATTERN.matcher(text);
        while (matcher.find()) {
            results.add(PiiDetectionResult.builder()
                    .piiType(PiiType.IP_ADDRESS)
                    .piiValue(matcher.group())
                    .start(matcher.start())
                    .end(matcher.end())
                    .confidence(0.90)
                    .build());
        }
        return results;
    }
}