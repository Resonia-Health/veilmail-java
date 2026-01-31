package xyz.veilmail.sdk.exceptions;

import java.util.Map;

/**
 * Thrown when access is denied due to insufficient permissions (403).
 */
public class ForbiddenException extends VeilMailException {
    public ForbiddenException(String message) {
        this(message, null, null);
    }

    public ForbiddenException(String message, String errorCode, Map<String, Object> details) {
        super(message, errorCode, 403, details);
    }
}
