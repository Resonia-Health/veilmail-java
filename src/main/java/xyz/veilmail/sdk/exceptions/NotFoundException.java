package xyz.veilmail.sdk.exceptions;

import java.util.Map;

/**
 * Thrown when the requested resource is not found (404).
 */
public class NotFoundException extends VeilMailException {
    public NotFoundException(String message) {
        this(message, null, null);
    }

    public NotFoundException(String message, String errorCode, Map<String, Object> details) {
        super(message, errorCode, 404, details);
    }
}
