package http.requestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Uri {
    private static final Logger logger = LoggerFactory.getLogger(Uri.class);
    private static final String DIRECTORY = "src/main/resources/static";

    private final String uri;

    Uri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
    }

    public boolean corresponds(String path) {
        return uri.equals(path);
    }

    boolean hasResource() {
        File file = new File(DIRECTORY + uri);
        return file.exists();
    }
}
