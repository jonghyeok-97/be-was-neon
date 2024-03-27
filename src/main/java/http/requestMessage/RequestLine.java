package http.requestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.path.PostPath;

import java.util.Optional;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

    private static final int METHOD_POSITION = 0;
    private static final int URI_POSITION = 1;
    private static final String REQUESTLINE_PARSOR = " ";

    private final Method method;
    private final Uri uri;

    // Method 는 requestLine에서 의 Method가 GET, POST인지 유효성 검증역할.
    RequestLine(final String requestLine) {
        logger.debug("리퀘스트 라인 : {}", requestLine);
        final String[] splited = requestLine.split(REQUESTLINE_PARSOR);
        method = new Method(splited[METHOD_POSITION]);
        uri = new Uri(splited[URI_POSITION]);
    }

    boolean isPOST() {
        return method.isPOST();
    }

    boolean isGet() {
        return method.isGet();
    }

    boolean has(final PostPath path) {
        return isPOST() && uri.isSame(path);
    }

    // GET 요청일 때만, 허락
    Optional<String> getUri() {
        if (method.isPOST()) {
            return Optional.empty();
        }
        return Optional.of(uri.getUri());
    }
}
