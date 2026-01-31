package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Geo and device analytics.
 */
public class Analytics {
    private final HttpClient http;

    public Analytics(HttpClient http) {
        this.http = http;
    }

    /**
     * Get organization-level geo analytics.
     */
    public Map<String, Object> geo(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/analytics/geo", params);
    }

    public Map<String, Object> geo() throws VeilMailException {
        return http.get("/v1/analytics/geo");
    }

    /**
     * Get organization-level device analytics.
     */
    public Map<String, Object> devices(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/analytics/devices", params);
    }

    public Map<String, Object> devices() throws VeilMailException {
        return http.get("/v1/analytics/devices");
    }

    /**
     * Get campaign-level geo analytics.
     */
    public Map<String, Object> campaignGeo(String campaignId, Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/campaigns/" + campaignId + "/analytics/geo", params);
    }

    public Map<String, Object> campaignGeo(String campaignId) throws VeilMailException {
        return http.get("/v1/campaigns/" + campaignId + "/analytics/geo");
    }

    /**
     * Get campaign-level device analytics.
     */
    public Map<String, Object> campaignDevices(String campaignId, Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/campaigns/" + campaignId + "/analytics/devices", params);
    }

    public Map<String, Object> campaignDevices(String campaignId) throws VeilMailException {
        return http.get("/v1/campaigns/" + campaignId + "/analytics/devices");
    }
}
