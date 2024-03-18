package webserver.request.line;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.response.Response;
import webserver.response.Response200;
import webserver.response.Response300;

import java.io.File;

public class RequestLine {
    private final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private static final int METHOD_POSITION = 0;
    private static final int URI_POSITION = 1;
    private final Method method;
    private final Uri uri;

    // Method 는 requestLine에서 의 Method가 GET, POST인지 유효성 검증역할.
    public RequestLine(final String requestLine) {
   //     logger.debug("리퀘스트 라인 : {}", requestLine);
        final String[] splited = requestLine.split(" ");
        method = new Method(splited[METHOD_POSITION]);
        uri = new Uri(splited[URI_POSITION]);
    }

    public boolean isPOST() {
        return method.isPOST();
    }

    public Response execute() {
        if (method.isPOST()) {
            final File file = uri.findFile();
            return new Response200(file);
        }
        return new Response300();
    }
}
