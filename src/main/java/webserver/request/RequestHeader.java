package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private final Map<String, String> headers;

    RequestHeader(final Map<String, String> headers) {
        this.headers = headers;
    }

    public String getSid() {
        final String cookieValue = headers.get("Cookie");
        final String sid = cookieValue.split("=")[1];
        return sid;
    }


}
