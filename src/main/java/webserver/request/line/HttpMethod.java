package webserver.request.line;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class HttpMethod {
    private final Logger logger = LoggerFactory.getLogger(HttpMethod.class);
    private final Type type;

    public HttpMethod(final String method) {
        logger.debug("METHOD : {}", method);
        type = Type.match(method);
    }

    private enum Type {
        GET, POST;

        private static Type match(final String method) {
            return Stream.of(values())
                    .filter(methodType -> methodType.toString().equals(method))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("404 에러"));
        }
    }
}

