package xyz.veilmail.sdk.exceptions;

import java.util.Map;

/**
 * Base exception for all Veil Mail API errors.
 */
public class VeilMailException extends Exception {
    private final String errorCode;
    private final Integer statusCode;
    private final Map<String, Object> details;

    public VeilMailException(String message) {
        this(message, null, null, null);
    }

    public VeilMailException(String message, String errorCode, Integer statusCode, Map<String, Object> details) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
