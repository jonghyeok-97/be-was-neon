package webserver.request.line;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Response;

import java.io.File;

public class HttpRequestLine {
    private final Logger logger = LoggerFactory.getLogger(HttpRequestLine.class);
    private static final int METHOD_POSITION = 0;
    private static final int URI_POSITION = 1;
    private final HttpMethod method;
    private final HttpUri uri;

    // HttpMethod 는 requestLine에서 의 Method가 GET, POST인지 유효성 검증역할.
    public HttpRequestLine(final String requestLine) {
   //     logger.debug("리퀘스트 라인 : {}", requestLine);
        final String[] splited = requestLine.split(" ");
        method = new HttpMethod(splited[METHOD_POSITION]);
        uri = new HttpUri(splited[URI_POSITION]);
    }

    public boolean isPOST() {
        return method.isPOST();
    }

    public Response execute() {

        final File file = uri.findFile();
        return new Response(file);
    }
}
