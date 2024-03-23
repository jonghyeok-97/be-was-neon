package webserver.handler;

import webserver.request.Request;
import webserver.response.Response;
import webserver.response.StatusLine;

import java.io.IOException;
import java.util.Optional;

public class GetHandler implements Handler {
    private final String uri;

    GetHandler(final String uri) {
        this.uri = uri;
    }

    public Response handle() throws IOException {
        final FileHandler fileHandler = FileHandler.createFileHandler(uri);
        final String subTypeOfMIME = fileHandler.findSubTypeOfMIME();
        final byte[] fileData = fileHandler.read();

        return new Response.Builder(StatusLine.OK.getValue())
                .contentType(subTypeOfMIME)
                .contentLength(fileData)
                .build();
    }
}
