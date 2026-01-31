package xyz.veilmail.sdk.exceptions;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Thrown when PII is detected in email content (422).
 */
public class PiiDetectedException extends VeilMailException {
    private final List<String> piiTypes;

    public PiiDetectedException(String message) {
        this(message, Collections.emptyList(), null, null);
    }

    public PiiDetectedException(String message, List<String> piiTypes, String errorCode, Map<String, Object> details) {
        super(message, errorCode, 422, details);
        this.piiTypes = piiTypes != null ? piiTypes : Collections.emptyList();
    }

    public List<String> getPiiTypes() {
        return piiTypes;
    }
}
