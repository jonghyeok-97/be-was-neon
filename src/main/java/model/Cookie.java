package model;

import java.util.UUID;

public class Cookie {
    private final String sid;

    private Cookie(final String sid) {
        this.sid = sid;
    }

    public static Cookie createCookie() {
        return new Cookie(UUID.randomUUID().toString());
    }

    public String getSid() {
        return sid;
    }
}
