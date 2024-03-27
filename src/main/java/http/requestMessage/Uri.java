package http.requestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.FileHandler;
import webserver.path.PostPath;

public class Uri {
    private static final Logger logger = LoggerFactory.getLogger(Uri.class);

    private final String uri;

    Uri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
    }

    boolean isSame(final PostPath path) {
        return path.getPath().equals(uri);
    }

    public String getUri() {
        return uri;
    }
}
