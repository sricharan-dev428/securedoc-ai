package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailDetector implements PiiDetector {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
    );

    @Override
    public List<PiiDetectionResult> detect(String text) {
        List<PiiDetectionResult> results = new ArrayList<>();
        Matcher matcher = EMAIL_PATTERN.matcher(text);

        while (matcher.find()) {
            results.add(PiiDetectionResult.builder()
                    .piiType(PiiType.EMAIL)
                    .piiValue(matcher.group())
                    .start(matcher.start())
                    .end(matcher.end())
                    .confidence(0.95)
                    .build());
        }

        return results;
    }
}