package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.*;

/**
 * Email sending and management.
 */
public class Emails {
    private final HttpClient http;

    public Emails(HttpClient http) {
        this.http = http;
    }

    /**
     * Send a single email.
     *
     * @param params Email parameters (from, to, subject, html, etc.)
     * @return The created email
     */
    public Map<String, Object> send(Map<String, Object> params) throws VeilMailException {
        Map<String, Object> body = new LinkedHashMap<>(params);
        // Ensure 'to' is always an array
        Object to = body.get("to");
        if (to instanceof String) {
            body.put("to", Collections.singletonList(to));
        }
        // Ensure 'cc' is an array if present
        Object cc = body.get("cc");
        if (cc instanceof String) {
            body.put("cc", Collections.singletonList(cc));
        }
        // Ensure 'bcc' is an array if present
        Object bcc = body.get("bcc");
        if (bcc instanceof String) {
            body.put("bcc", Collections.singletonList(bcc));
        }
        return http.post("/v1/emails", body);
    }

    /**
     * Send a batch of up to 100 emails.
     *
     * @param emails List of email params (same format as send)
     * @return Batch result with per-email status
     */
    public Map<String, Object> sendBatch(List<Map<String, Object>> emails) throws VeilMailException {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("emails", emails);
        return http.post("/v1/emails/batch", body);
    }

    /**
     * List emails with optional filters.
     *
     * @param params Optional filters (limit, cursor, status, tag, after, before)
     * @return Paginated list with data, hasMore, nextCursor
     */
    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/emails", params);
    }

    /**
     * List emails with no filters.
     */
    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/emails");
    }

    /**
     * Get a single email by ID.
     */
    public Map<String, Object> get(String id) throws VeilMailException {
        return http.get("/v1/emails/" + id);
    }

    /**
     * Cancel a scheduled email.
     */
    public Map<String, Object> cancel(String id) throws VeilMailException {
        return http.post("/v1/emails/" + id + "/cancel");
    }

    /**
     * Reschedule a scheduled email.
     *
     * @param id     Email ID
     * @param params Update parameters (scheduledFor)
     * @return Updated email
     */
    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        return http.patch("/v1/emails/" + id, params);
    }

    /**
     * Get tracked link analytics for a specific email.
     */
    public Map<String, Object> links(String id, Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/emails/" + id + "/links", params);
    }

    public Map<String, Object> links(String id) throws VeilMailException {
        return http.get("/v1/emails/" + id + "/links");
    }
}
