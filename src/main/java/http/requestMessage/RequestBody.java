package http.requestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
