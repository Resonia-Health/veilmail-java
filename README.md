# Veil Mail Java SDK

Official Java SDK for the [Veil Mail](https://veilmail.xyz) API. Send emails with built-in PII protection.

## Requirements

- Java 11+
- Jackson Databind 2.17+

## Installation

### Maven

```xml
<dependency>
    <groupId>xyz.veilmail</groupId>
    <artifactId>veilmail-java</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'xyz.veilmail:veilmail-java:0.1.0'
```

## Quick Start

```java
import xyz.veilmail.sdk.VeilMail;
import java.util.Map;

VeilMail client = new VeilMail("veil_live_xxxxx");

Map<String, Object> email = client.emails().send(Map.of(
    "from", "hello@yourdomain.com",
    "to", "user@example.com",
    "subject", "Hello from Java!",
    "html", "<h1>Welcome!</h1>"
));

System.out.println(email.get("id"));     // email_xxxxx
System.out.println(email.get("status")); // queued
```

## Configuration

```java
VeilMail client = new VeilMail(
    "veil_live_xxxxx",
    "https://custom-api.example.com",  // custom base URL
    10_000                              // timeout in ms
);
```

## Emails

```java
// Send with named sender
Map<String, Object> email = client.emails().send(Map.of(
    "from", "Alice <alice@yourdomain.com>",
    "to", List.of("bob@example.com"),
    "subject", "Hello",
    "html", "<p>Hello Bob!</p>",
    "tags", List.of("welcome")
));

// Send with template
Map<String, Object> email = client.emails().send(Map.of(
    "from", "hello@yourdomain.com",
    "to", "user@example.com",
    "templateId", "tmpl_xxx",
    "templateData", Map.of("name", "Alice")
));

// Send with attachments
Map<String, Object> email = client.emails().send(Map.of(
    "from", "hello@yourdomain.com",
    "to", "user@example.com",
    "subject", "Invoice",
    "html", "<p>Attached is your invoice.</p>",
    "attachments", List.of(Map.of(
        "filename", "invoice.pdf",
        "content", base64Content,
        "contentType", "application/pdf"
    ))
));

// Batch send (up to 100)
Map<String, Object> result = client.emails().sendBatch(List.of(
    Map.of("from", "hi@yourdomain.com", "to", List.of("u1@ex.com"), "subject", "Hi", "html", "<p>Hi!</p>"),
    Map.of("from", "hi@yourdomain.com", "to", List.of("u2@ex.com"), "subject", "Hi", "html", "<p>Hi!</p>")
));

// List, get, cancel, reschedule
Map<String, Object> emails = client.emails().list(Map.of("status", "delivered", "limit", 10));
Map<String, Object> email = client.emails().get("email_xxx");
Map<String, Object> result = client.emails().cancel("email_xxx");
Map<String, Object> updated = client.emails().update("email_xxx", Map.of("scheduledFor", "2025-07-01T09:00:00Z"));
```

## Domains

```java
// Add and verify a domain
Map<String, Object> domain = client.domains().create(Map.of("domain", "mail.example.com"));
domain = client.domains().verify((String) domain.get("id"));

// Update tracking
domain = client.domains().update((String) domain.get("id"), Map.of(
    "trackOpens", true,
    "trackClicks", true
));

// List and delete
Map<String, Object> domains = client.domains().list();
client.domains().delete((String) domain.get("id"));
```

## Templates

```java
Map<String, Object> template = client.templates().create(Map.of(
    "name", "Welcome",
    "subject", "Welcome, {{name}}!",
    "html", "<h1>Hello {{name}}</h1>",
    "variables", List.of(Map.of("name", "name", "type", "string", "required", true))
));

// Preview
Map<String, Object> preview = client.templates().preview(Map.of(
    "html", "<h1>Hello {{name}}</h1>",
    "variables", Map.of("name", "Alice")
));
```

## Audiences & Subscribers

```java
Map<String, Object> audience = client.audiences().create(Map.of("name", "Newsletter"));
var subs = client.audiences().subscribers((String) audience.get("id"));

// Add subscriber
Map<String, Object> subscriber = subs.add(Map.of(
    "email", "user@example.com",
    "firstName", "Alice",
    "lastName", "Smith"
));

// List, import, export
Map<String, Object> subscribers = subs.list(Map.of("status", "active", "limit", 50));
Map<String, Object> result = subs.importSubscribers(Map.of("csvData", "email,firstName\nuser@example.com,Bob"));
String csv = subs.export(Map.of("status", "active"));

// Activity timeline
Map<String, Object> events = subs.activity((String) subscriber.get("id"), Map.of("limit", 20));
```

## Campaigns

```java
Map<String, Object> campaign = client.campaigns().create(Map.of(
    "name", "Summer Sale",
    "subject", "50% Off!",
    "from", "Store <deals@yourdomain.com>",
    "audienceId", "aud_xxx",
    "html", "<h1>Summer Sale!</h1>"
));

// Schedule, send, pause, resume, cancel
client.campaigns().schedule((String) campaign.get("id"), Map.of("scheduledAt", "2025-06-15T10:00:00Z"));
client.campaigns().send((String) campaign.get("id"));
client.campaigns().pause((String) campaign.get("id"));
client.campaigns().resume((String) campaign.get("id"));
client.campaigns().cancel((String) campaign.get("id"));
```

## Webhooks

```java
Map<String, Object> webhook = client.webhooks().create(Map.of(
    "url", "https://yourdomain.com/webhooks/veilmail",
    "events", List.of("email.delivered", "email.bounced")
));

// Test and rotate secret
Map<String, Object> result = client.webhooks().test((String) webhook.get("id"));
webhook = client.webhooks().rotateSecret((String) webhook.get("id"));
```

### Signature Verification

```java
import xyz.veilmail.sdk.Webhook;

// In a Spring controller
@PostMapping("/webhooks/veilmail")
public ResponseEntity<Void> handleWebhook(
        @RequestBody String body,
        @RequestHeader("X-Signature-Hash") String signature) {

    if (!Webhook.verifySignature(body, signature, webhookSecret)) {
        return ResponseEntity.status(401).build();
    }

    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> event = mapper.readValue(body, Map.class);

    switch ((String) event.get("type")) {
        case "email.delivered":
            // Handle delivery
            break;
        case "email.bounced":
            // Handle bounce
            break;
    }

    return ResponseEntity.ok().build();
}
```

## Topics

```java
Map<String, Object> topic = client.topics().create(Map.of(
    "name", "Product Updates",
    "isDefault", true
));
Map<String, Object> topics = client.topics().list(Map.of("active", true));

// Subscriber preferences
Map<String, Object> prefs = client.topics().getPreferences("aud_xxx", "sub_xxx");
client.topics().setPreferences("aud_xxx", "sub_xxx", Map.of(
    "topics", List.of(
        Map.of("topicId", "topic_xxx", "subscribed", true),
        Map.of("topicId", "topic_yyy", "subscribed", false)
    )
));
```

## Contact Properties

```java
Map<String, Object> prop = client.properties().create(Map.of(
    "key", "company",
    "name", "Company Name",
    "type", "text"
));

// Set values for a subscriber
client.properties().setValues("aud_xxx", "sub_xxx", Map.of("company", "Acme Corp"));
Map<String, Object> values = client.properties().getValues("aud_xxx", "sub_xxx");
```

## Error Handling

```java
import xyz.veilmail.sdk.exceptions.*;

try {
    client.emails().send(Map.of(
        "from", "hello@yourdomain.com",
        "to", "user@example.com",
        "subject", "Hello",
        "html", "<p>Hi!</p>"
    ));
} catch (RateLimitException e) {
    System.out.println("Rate limited. Retry after " + e.getRetryAfter() + "s");
} catch (PiiDetectedException e) {
    System.out.println("PII detected: " + e.getPiiTypes());
} catch (ValidationException e) {
    System.out.println("Validation error: " + e.getMessage());
} catch (AuthenticationException e) {
    System.out.println("Invalid API key");
} catch (VeilMailException e) {
    System.out.println("API error: " + e.getMessage() + " (code: " + e.getErrorCode() + ")");
}
```

## License

MIT
