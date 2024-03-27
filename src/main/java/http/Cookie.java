package http;

import java.util.Objects;

public class Cookie {
    private final String sid;
    private int maxAge;
    private String path;

    public Cookie(final String sid) {
        this.sid = "sid=" + sid;
    }

    public void setMaxAge(final int maxAge) {
        this.maxAge = maxAge;
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
