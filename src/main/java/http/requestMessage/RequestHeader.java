package http.requestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private final Map<String, String> headers;

    RequestHeader(final Map<String, String> headers) {
        this.headers = headers;
    }

    public Optional<String> getSessionID() {
        final Optional<String> optCookieLine = Optional.ofNullable(headers.get("Cookie"));
        return optCookieLine.map(cookieLine -> cookieLine.split(";"))
                .flatMap(cookies -> Stream.of(cookies)
                        .filter(cookie -> cookie.contains("SID"))
                        .map(sidLine -> sidLine.trim().split("=")[1])
                        .findFirst());
    }

    Optional<Integer> getContentLength() {
        Optional<String> optContentLength = Optional.ofNullable(headers.get("Content-Length"));
        return optContentLength.map(Integer::parseInt);
    }
}
