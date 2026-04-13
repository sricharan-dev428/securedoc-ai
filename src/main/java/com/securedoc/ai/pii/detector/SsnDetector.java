package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SsnDetector implements PiiDetector {
    private static final Pattern SSN_PATTERN = Pattern.compile("\\d{3}-\\d{2}-\\d{4}");

    @Override
    public List<PiiDetectionResult> detect(String text) {
        List<PiiDetectionResult> results = new ArrayList<>();
        Matcher matcher = SSN_PATTERN.matcher(text);
        while (matcher.find()) {
            results.add(PiiDetectionResult.builder()
                    .piiType(PiiType.SSN)
                    .piiValue(matcher.group())
                    .start(matcher.start())
                    .end(matcher.end())
                    .confidence(0.90)
                    .build());

        }
        return results;
    }
}
