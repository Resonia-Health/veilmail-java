package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Email template management.
 */
public class Templates {
    private final HttpClient http;

    public Templates(HttpClient http) {
        this.http = http;
    }

    /**
     * Create a new template.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/templates", params);
        return unwrap(response);
    }

    /**
     * List all templates.
     */
    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/templates", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/templates");
    }

    /**
     * Get a single template by ID.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> get(String id) throws VeilMailException {
        Map<String, Object> response = http.get("/v1/templates/" + id);
        return unwrap(response);
    }

    /**
     * Update a template.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.patch("/v1/templates/" + id, params);
        return unwrap(response);
    }

    /**
     * Preview a template with variables.
     */
    public Map<String, Object> preview(Map<String, Object> params) throws VeilMailException {
        return http.post("/v1/templates/preview", params);
    }

    /**
     * Delete a template.
     */
    public void delete(String id) throws VeilMailException {
        http.delete("/v1/templates/" + id);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> unwrap(Map<String, Object> response) {
        if (response.containsKey("data") && response.get("data") instanceof Map) {
            return (Map<String, Object>) response.get("data");
        }
        return response;
    }
}
