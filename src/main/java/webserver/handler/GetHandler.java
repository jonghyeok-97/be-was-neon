package webserver.handler;

import http.responseMessage.Response;
import http.responseMessage.StatusLine;

import java.io.IOException;

public class GetHandler implements Handler {
    private final String uri;

    GetHandler(final String uri) {
        this.uri = uri;
    }

    public Response handle() throws IOException {
        final FileHandler fileHandler = FileHandler.createFileHandler(uri);
        final String subTypeOfMIME = fileHandler.findSubTypeOfMIME();
        final byte[] fileData = fileHandler.read();

        return new Response.Builder(StatusLine.OK_200)
                .contentType(subTypeOfMIME)
                .contentLength(fileData)
                .build();
    }
}
