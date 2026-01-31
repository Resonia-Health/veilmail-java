package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * RSS feed management.
 */
public class Feeds {
    private final HttpClient http;

    public Feeds(HttpClient http) {
        this.http = http;
    }

    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        return http.post("/v1/feeds", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/feeds");
    }

    public Map<String, Object> get(String id) throws VeilMailException {
        return http.get("/v1/feeds/" + id);
    }

    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        return http.put("/v1/feeds/" + id, params);
    }

    public void delete(String id) throws VeilMailException {
        http.delete("/v1/feeds/" + id);
    }

    public Map<String, Object> poll(String id) throws VeilMailException {
        return http.post("/v1/feeds/" + id + "/poll");
    }

    public Map<String, Object> pause(String id) throws VeilMailException {
        return http.post("/v1/feeds/" + id + "/pause");
    }

    public Map<String, Object> resume(String id) throws VeilMailException {
        return http.post("/v1/feeds/" + id + "/resume");
    }

    public Map<String, Object> listItems(String feedId, Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/feeds/" + feedId + "/items", params);
    }

    public Map<String, Object> listItems(String feedId) throws VeilMailException {
        return http.get("/v1/feeds/" + feedId + "/items");
    }
}
