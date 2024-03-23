package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.FileHandler;
import webserver.utils.PostRequestPath;

public class Uri {
    private static final Logger logger = LoggerFactory.getLogger(Uri.class);

    private final String uri;

    Uri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
        validate(uri);
    }

    boolean isSame(final PostRequestPath path) {
        return path.getPath().equals(uri);
    }

    String getUri() {
        return uri;
    }

    private void validate(final String uri) {
        if (!FileHandler.isExistFile(uri) && !PostRequestPath.has(uri)) {
            throw new IllegalArgumentException("404에러");
        }
    }
}
