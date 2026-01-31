package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Domain management for email sending.
 */
public class Domains {
    private final HttpClient http;

    public Domains(HttpClient http) {
        this.http = http;
    }

    /**
     * Add a new domain for verification.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/domains", params);
        return unwrap(response);
    }

    /**
     * List all domains.
     */
    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/domains", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/domains");
    }

    /**
     * Get a single domain by ID.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> get(String id) throws VeilMailException {
        Map<String, Object> response = http.get("/v1/domains/" + id);
        return unwrap(response);
    }

    /**
     * Update domain settings (tracking, etc.).
     */
    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        return http.patch("/v1/domains/" + id, params);
    }

    /**
     * Trigger domain verification.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> verify(String id) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/domains/" + id + "/verify");
        return unwrap(response);
    }

    /**
     * Delete a domain.
     */
    public void delete(String id) throws VeilMailException {
        http.delete("/v1/domains/" + id);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> unwrap(Map<String, Object> response) {
        if (response.containsKey("data") && response.get("data") instanceof Map) {
            return (Map<String, Object>) response.get("data");
        }
        return response;
    }
}
