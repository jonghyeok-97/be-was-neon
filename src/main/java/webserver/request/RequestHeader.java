package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

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

    Optional<Integer> getContentLength() {
        Optional<String> optContentLength = Optional.ofNullable(headers.get("Content-Length"));
        return optContentLength.map(Integer::parseInt);
    }
}
