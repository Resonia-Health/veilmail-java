package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Webhook endpoint management.
 */
public class Webhooks {
    private final HttpClient http;

    public Webhooks(HttpClient http) {
        this.http = http;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/webhooks", params);
        return unwrap(response);
    }

    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/webhooks", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/webhooks");
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> get(String id) throws VeilMailException {
        Map<String, Object> response = http.get("/v1/webhooks/" + id);
        return unwrap(response);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.patch("/v1/webhooks/" + id, params);
        return unwrap(response);
    }

    public void delete(String id) throws VeilMailException {
        http.delete("/v1/webhooks/" + id);
    }

    public Map<String, Object> test(String id) throws VeilMailException {
        return http.post("/v1/webhooks/" + id + "/test");
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> rotateSecret(String id) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/webhooks/" + id + "/rotate-secret");
        return unwrap(response);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> unwrap(Map<String, Object> response) {
        if (response.containsKey("data") && response.get("data") instanceof Map) {
            return (Map<String, Object>) response.get("data");
        }
        return response;
    }
}
