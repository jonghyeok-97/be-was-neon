package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private final Map<String, String> headers;

    RequestHeader(final List<String> messages) {
        try {
            headers = parseTypeAndValue(messages);
        } catch (PatternSyntaxException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("404에러");
        }
    }

    private Map<String, String> parseTypeAndValue(final List<String> messages) {
        final Map<String, String> _headers = new HashMap<>();
        messages.forEach(message -> {
            final String[] splited = message.split(":");
            String type = splited[0].trim();
            String value = splited[1].trim();
            _headers.put(type, value);
        });
        return _headers;
    }
}
