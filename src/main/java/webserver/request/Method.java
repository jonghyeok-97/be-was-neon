package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class Method {
    private static final Logger logger = LoggerFactory.getLogger(Method.class);

    private final Type type;

    Method(final String method) {
        logger.debug("메소드 : {}", method);
        type = Type.match(method);
    }

    boolean isPOST() {
        return type == Type.POST;
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


