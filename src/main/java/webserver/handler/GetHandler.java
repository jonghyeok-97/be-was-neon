package webserver.handler;

import webserver.request.Request;
import webserver.response.MIME;
import webserver.response.Response;
import webserver.response.StatusLine;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class GetHandler implements Handler {
    private final Request request;

    GetHandler(final Request request) {
        this.request = request;
    }

    public Response handle() throws IOException {
        final Optional<String> optUri = request.getUri();
        final FileHandler fileHandler = FileHandler.createFileHandler(optUri.get());
        final String fileType = fileHandler.findFileType();
        final MIME mime = MIME.find(fileType);
        final byte[] fileData = fileHandler.read();

        return new Response.Builder(StatusLine.OK.getValue())
                .contentType(mime.getSubType())
                .contentLength(fileData)
                .build();
    }
}
