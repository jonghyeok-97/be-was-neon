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



    public String getSid() {
        return sid;
    }

    public String getMaxAge() {
        return "Max-Age=" + maxAge;
    }
}
