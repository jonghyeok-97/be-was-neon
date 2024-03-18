package webserver.request;

import webserver.response.Response;
import webserver.request.body.RequestBody;
import webserver.request.line.RequestLine;
import webserver.response.Response200;
import webserver.response.Response300;

import java.io.File;
import java.util.Optional;

public class Request {
    private final RequestLine line;
    private final Optional<RequestBody> optBody;

    public Request(final RequestLine _line, final Optional<RequestBody> _optBody) {
        this.line = _line;
        this.optBody = _optBody;
    }

    public Response respond() {
        optBody.ifPresent(RequestBody::addUserToDB);
        final Optional<File> optFile = line.getFile();
        if (optFile.isPresent()) {
            return new Response200(optFile.get());
        }
        return new Response300();
    }
}
