package webserver.request;

import webserver.request.body.HttpRequestBody;
import webserver.request.line.HttpRequestLine;

import java.util.List;

public class HttpRequestFactory {
    private final List<String> requestMessage;

    public HttpRequestFactory(final List<String> _requestMessage) {
        requestMessage = _requestMessage;
    }

    public HttpRequestLine createRequestLine() {
        final String requestLine = requestMessage.get(0);
        return new HttpRequestLine(requestLine);
    }

    public HttpRequestBody createRequestBody() {
        final String body = requestMessage.get(requestMessage.size() - 1);
        return new HttpRequestBody(body);
    }
}
