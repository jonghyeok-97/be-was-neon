package http;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cookie {
    private final Map<String, String> attributes;

    public Cookie() {
        attributes = new LinkedHashMap<>();
    }

    public Cookie set(final String key, final String value) {
        attributes.put(key, value);
        return this;
    }

    public String getCookie() {
        final StringBuilder sb = new StringBuilder();
        attributes.forEach((key, value) -> {
            final String cookieMessage = convertToMessage(key, value);
            sb.append(cookieMessage);
        });
        return sb.toString();
    }

    private String convertToMessage(final String key, final String value) {
        return key + "=" + value + ";";
    }
}
