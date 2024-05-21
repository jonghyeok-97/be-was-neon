package http.requestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Uri {
    private static final Logger logger = LoggerFactory.getLogger(Uri.class);

    private final String uri;

    Uri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
