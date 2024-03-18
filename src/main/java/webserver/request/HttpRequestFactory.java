package webserver.request;

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


}
