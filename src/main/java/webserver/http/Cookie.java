package webserver.http;

import java.util.Objects;
import java.util.UUID;

public class Cookie {
    private String sid;

    public Cookie(final String sid) {
        this.sid = sid;
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
