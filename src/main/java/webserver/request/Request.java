package webserver.request;

import webserver.response.Response;
import webserver.request.body.RequestBody;
import webserver.request.line.RequestLine;

import java.util.Optional;

public class Request {
    private final RequestLine requestLine;
    private final Optional<RequestBody> body;

    public Request(final RequestLine _requestLine, final Optional<RequestBody> _body) {
        this.requestLine = _requestLine;
        this.body = _body;
    }

    public Response respond() {
        body.ifPresent(
                RequestBody::addUserToDB
        );
        return requestLine.execute();
    }
}
