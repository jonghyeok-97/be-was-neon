package model;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(sid, cookie.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid);
    }
}
