package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Uri {
    private static final Logger logger = LoggerFactory.getLogger(Uri.class);
    private static final String RESOURCES_BASE_PATH = "src/main/resources/static";

    private final String uri;

    Uri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
        validate(uri);
    }

    boolean isLogin() {
        return uri.contains("login");
    }

    boolean isRegister() {
        return uri.contains("create");
    }

    File findFile() {
        return new File(RESOURCES_BASE_PATH + uri);
    }

    private void validate(final String uri) {
        if (!uri.contains(".") && !uri.contains("create") && !uri.contains("login")) {
            throw new IllegalArgumentException("404에러");
        }
    }
}
