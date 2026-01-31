package xyz.veilmail.sdk;

import xyz.veilmail.sdk.resources.*;

/**
 * Veil Mail API client.
 *
 * <pre>{@code
 * VeilMail client = new VeilMail("veil_live_xxxxx");
 *
 * Map<String, Object> email = client.emails().send(Map.of(
 *     "from", "hello@yourdomain.com",
 *     "to", "user@example.com",
 *     "subject", "Hello from Java!",
 *     "html", "<h1>Welcome!</h1>"
 * ));
 * }</pre>
 */
public class VeilMail {
    private final Emails emails;
    private final Domains domains;
    private final Templates templates;
    private final Audiences audiences;
    private final Campaigns campaigns;
    private final Webhooks webhooks;
    private final Topics topics;
    private final Properties properties;
    private final Sequences sequences;
    private final Feeds feeds;
    private final Forms forms;
    private final Analytics analytics;

    /**
     * Create a new Veil Mail client with default settings.
     *
     * @param apiKey Your API key (must start with veil_live_ or veil_test_)
     * @throws IllegalArgumentException if the API key format is invalid
     */
    public VeilMail(String apiKey) {
        this(apiKey, null, 0);
    }

    /**
     * Create a new Veil Mail client with custom settings.
     *
     * @param apiKey    Your API key (must start with veil_live_ or veil_test_)
     * @param baseUrl   Custom API base URL (null for default)
     * @param timeoutMs Request timeout in milliseconds (0 for default)
     * @throws IllegalArgumentException if the API key format is invalid
     */
    public VeilMail(String apiKey, String baseUrl, int timeoutMs) {
        if (apiKey == null || (!apiKey.startsWith("veil_live_") && !apiKey.startsWith("veil_test_"))) {
            throw new IllegalArgumentException(
                    "Invalid API key format. Key must start with \"veil_live_\" or \"veil_test_\"."
            );
        }

        HttpClient http = new HttpClient(apiKey, baseUrl, timeoutMs);

        this.emails = new Emails(http);
        this.domains = new Domains(http);
        this.templates = new Templates(http);
        this.audiences = new Audiences(http);
        this.campaigns = new Campaigns(http);
        this.webhooks = new Webhooks(http);
        this.topics = new Topics(http);
        this.properties = new Properties(http);
        this.sequences = new Sequences(http);
        this.feeds = new Feeds(http);
        this.forms = new Forms(http);
        this.analytics = new Analytics(http);
    }

    public Emails emails() { return emails; }
    public Domains domains() { return domains; }
    public Templates templates() { return templates; }
    public Audiences audiences() { return audiences; }
    public Campaigns campaigns() { return campaigns; }
    public Webhooks webhooks() { return webhooks; }
    public Topics topics() { return topics; }
    public Properties properties() { return properties; }
    public Sequences sequences() { return sequences; }
    public Feeds feeds() { return feeds; }
    public Forms forms() { return forms; }
    public Analytics analytics() { return analytics; }
}
