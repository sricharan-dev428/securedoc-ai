package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DateOfBirthDetector implements PiiDetector {

    private static final Pattern DOB_PATTERN = Pattern.compile(
            "\\b\\d{2}[/-]\\d{2}[/-]\\d{4}\\b"
    );

    @Override
    public List<PiiDetectionResult> detect(String text) {
        List<PiiDetectionResult> results = new ArrayList<>();
        Matcher matcher = DOB_PATTERN.matcher(text);
        while (matcher.find()) {
            results.add(PiiDetectionResult.builder()
                    .piiType(PiiType.DATE_OF_BIRTH)
                    .piiValue(matcher.group())
                    .start(matcher.start())
                    .end(matcher.end())
                    .confidence(0.75)
                    .build());
        }
        return results;
    }
}