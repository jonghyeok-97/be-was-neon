package http;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private final String sessionId;
    private final Map<String, String> attributes;

    public Cookie(final String sessionId) {
        this.sessionId = sessionId;
        this.attributes = new HashMap<>();
    }

    public void set(final String key, final String value) {
        attributes.put(key, value);
    }

    public String get() {
        final StringBuilder sb = new StringBuilder();
        sb.append(createCookieString("SID", sessionId));
        attributes.forEach((key, value) -> {
            final String cookieString = createCookieString(key, value);
            sb.append(cookieString);
        });
        return sb.toString();
    }

    private String createCookieString(final String key, final String value) {
        return key + "=" + value + ";";
    }
}
