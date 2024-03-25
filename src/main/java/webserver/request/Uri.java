package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.FileHandler;
import webserver.handler.PostHandler;
import webserver.path.PostPath;

public class Uri {
    private static final Logger logger = LoggerFactory.getLogger(Uri.class);

    private final String uri;

    Uri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
        validate(uri);
    }

    boolean isSame(final PostPath path) {
        return path.getPath().equals(uri);
    }

    String getUri() {
        return uri;
    }

    // URI에 해당하는 파일도 존재하지 않고, POST에 해당하는 URI도 아니라면 404에러
    private void validate(final String uri) {
        if (!FileHandler.isExistFile(uri) && !PostPath.has(uri)) {
            throw new IllegalArgumentException("404에러");
        }
    }
}
