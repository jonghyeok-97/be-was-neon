package webserver.request.line;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class HttpUri {
    private final Logger logger = LoggerFactory.getLogger(HttpUri.class);
    static final String RESOURCES_BASE_PATH = "src/main/resources/static";
    private final String uri;

    public HttpUri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
        validateExtension(uri);
    }

    public File findFile() {
        return new File(RESOURCES_BASE_PATH + uri);
    }

    private void validateExtension(final String uri) {
        if (!uri.contains(".")) {
            throw new IllegalArgumentException("404에러");
        }
    }
}
