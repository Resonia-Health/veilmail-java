package xyz.veilmail.sdk.exceptions;

import java.util.Map;

/**
 * Thrown when request validation fails (400).
 */
public class ValidationException extends VeilMailException {
    public ValidationException(String message) {
        this(message, null, null);
    }

    public ValidationException(String message, String errorCode, Map<String, Object> details) {
        super(message, errorCode, 400, details);
    }
}
