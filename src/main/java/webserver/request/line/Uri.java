package webserver.request.line;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Uri {
    private final Logger logger = LoggerFactory.getLogger(Uri.class);
    private static final String RESOURCES_BASE_PATH = "src/main/resources/static";
    private final String uri;

    public Uri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
        validate(uri);
    }

    public File findFile() {
        return new File(RESOURCES_BASE_PATH + uri);
    }

    private void validate(final String uri) {
        if (!uri.contains(".") && !uri.contains("create")) {
            throw new IllegalArgumentException("404에러");
        }
    }
}
