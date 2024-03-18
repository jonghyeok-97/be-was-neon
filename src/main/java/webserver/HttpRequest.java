package webserver;

import webserver.request.body.HttpRequestBody;
import webserver.request.line.HttpRequestLine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final Optional<HttpRequestBody> body;

    public HttpRequest(final HttpRequestLine _requestLine, final Optional<HttpRequestBody> _body) {
        this.requestLine = _requestLine;
        this.body = _body;
    }

    public Response respond() {
        body.ifPresent(
                HttpRequestBody::addUserToDB
        );
        return requestLine.execute();
    }
}
