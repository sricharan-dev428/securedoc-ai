package com.securedoc.ai.pii.detector;

import com.securedoc.ai.pii.model.PiiDetectionResult;
import com.securedoc.ai.pii.model.PiiType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CreditCardDetector implements PiiDetector {

    private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile(
            "\\d{4}[\\s-]?\\d{4}[\\s-]?\\d{4}[\\s-]?\\d{4}"
    );

    @Override
    public List<PiiDetectionResult> detect(String text) {
        List<PiiDetectionResult> results = new ArrayList<>();
        Matcher matcher = CREDIT_CARD_PATTERN.matcher(text);
        while (matcher.find()) {
            results.add(PiiDetectionResult.builder()
                    .piiType(PiiType.CREDIT_CARD)
                    .piiValue(matcher.group())
                    .start(matcher.start())
                    .end(matcher.end())
                    .confidence(0.85)
                    .build());
        }
        return results;
    }
}