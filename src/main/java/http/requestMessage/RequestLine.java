package http.requestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public boolean corresponds(String path) {
        return uri.corresponds(path);
    }
}
