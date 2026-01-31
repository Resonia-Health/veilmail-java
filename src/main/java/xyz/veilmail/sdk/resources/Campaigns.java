package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Campaign management.
 */
public class Campaigns {
    private final HttpClient http;

    public Campaigns(HttpClient http) {
        this.http = http;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/campaigns", params);
        return unwrap(response);
    }

    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/campaigns", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/campaigns");
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> get(String id) throws VeilMailException {
        Map<String, Object> response = http.get("/v1/campaigns/" + id);
        return unwrap(response);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.patch("/v1/campaigns/" + id, params);
        return unwrap(response);
    }

    public void delete(String id) throws VeilMailException {
        http.delete("/v1/campaigns/" + id);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> schedule(String id, Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/campaigns/" + id + "/schedule", params);
        return unwrap(response);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> send(String id) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/campaigns/" + id + "/send");
        return unwrap(response);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> pause(String id) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/campaigns/" + id + "/pause");
        return unwrap(response);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> resume(String id) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/campaigns/" + id + "/resume");
        return unwrap(response);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> cancel(String id) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/campaigns/" + id + "/cancel");
        return unwrap(response);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> sendTest(String id, java.util.List<String> to) throws VeilMailException {
        return http.post("/v1/campaigns/" + id + "/test", Map.of("to", to));
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> clone(String id, Map<String, Object> options) throws VeilMailException {
        return http.post("/v1/campaigns/" + id + "/clone", options);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> clone(String id) throws VeilMailException {
        return http.post("/v1/campaigns/" + id + "/clone", Map.of());
    }

    public Map<String, Object> links(String id, Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/campaigns/" + id + "/links", params);
    }

    public Map<String, Object> links(String id) throws VeilMailException {
        return http.get("/v1/campaigns/" + id + "/links");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> unwrap(Map<String, Object> response) {
        if (response.containsKey("data") && response.get("data") instanceof Map) {
            return (Map<String, Object>) response.get("data");
        }
        return response;
    }
}
