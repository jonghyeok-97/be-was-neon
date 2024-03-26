package model;

import db.SessionDB;

import java.util.Objects;
import java.util.UUID;

public class Cookie {
    private final String sid;

    private Cookie(final String sid) {
        this.sid = sid;
    }

    public static Cookie createCookie() {
        Cookie cookie = new Cookie(UUID.randomUUID().toString());
        while (!SessionDB.has(cookie)) {
            cookie = new Cookie(UUID.randomUUID().toString());
        }
        return cookie;
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
