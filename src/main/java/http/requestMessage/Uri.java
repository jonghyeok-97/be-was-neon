package http.requestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Uri {
    private static final Logger logger = LoggerFactory.getLogger(Uri.class);

    private final String uri;

    Uri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
    }

    public boolean isSame(final String other) {
        return uri.equals(other);
    }

    public File createFile(final String path) {
        return new File(path + uri);
    }
}
