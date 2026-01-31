package xyz.veilmail.sdk.exceptions;

import java.util.Map;

/**
 * Thrown when the API key is invalid or missing (401).
 */
public class AuthenticationException extends VeilMailException {
    public AuthenticationException(String message) {
        this(message, null, null);
    }

    public AuthenticationException(String message, String errorCode, Map<String, Object> details) {
        super(message, errorCode, 401, details);
    }
}
