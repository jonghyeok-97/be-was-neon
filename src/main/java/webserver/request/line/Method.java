package webserver.request.line;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class Method {
    private final Logger logger = LoggerFactory.getLogger(Method.class);
    private final Type type;

    public Method(final String method) {
        logger.debug("METHOD : {}", method);
        type = Type.match(method);
    }

    public boolean isPOST() {
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


