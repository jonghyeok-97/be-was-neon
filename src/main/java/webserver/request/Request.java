package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.response.Response;
import webserver.request.body.RequestBody;
import webserver.request.line.RequestLine;
import webserver.response.Response200;
import webserver.response.Response300;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Request {
    private final Logger logger = LoggerFactory.getLogger(Request.class);
    private final RequestLine line;
    private final Optional<RequestBody> optBody;

    public Request(final RequestLine _line, final Optional<RequestBody> _optBody) {
        this.line = _line;
        this.optBody = _optBody;
    }

    public Response respond() throws IOException {
        optBody.ifPresent(RequestBody::addUserToDB);
        final Optional<File> optFile = line.findFile();
        if (optFile.isPresent()) {
            return new Response200(optFile.get());
        }
        return new Response300();
    }
}
