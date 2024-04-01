package http.requestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private static final Logger logger = LoggerFactory.getLogger(RequestBody.class);

    private final String body;

    RequestBody(final String body) {
        logger.debug("요청된 [BODY] -> " + body);
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    Map<String, String> getKeyValue() {
        final String[] querys = getQuerys(body);
        return getKeyValue(querys);
    }

    private String[] getQuerys(final String bodyLine) {
        return bodyLine.split("&");
    }

    private Map<String, String> getKeyValue(final String[] querys) {
        final Map<String, String> keyValue = new HashMap<>();
        Arrays.stream(querys)
                .forEach(query -> {
                    final String[] keyValues = query.split("=");
                    final String key = keyValues[0];
                    final String value = keyValues[1];
                    keyValue.put(key, value);
                });
        return keyValue;
    }
}
