package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.utils.FilePath;
import webserver.utils.PostRequestPath;

import java.io.File;

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

    File findFile() {
        return new File(FilePath.BASE.getPath() + uri);
    }

    private void validate(final String uri) {
        if (!uri.contains(".") && !uri.contains("create") && !uri.contains("login")) {
            throw new IllegalArgumentException("404에러");
        }
    }
}
