package webserver.handler;

import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GetHandler implements Handler {
    private static final Logger logger = LoggerFactory.getLogger(GetHandler.class);
    private final String uri;

    GetHandler(final String uri) {
        this.uri = uri;
    }

    public Response handle() {
        final FileHandler fileHandler = FileHandler.createFileHandler(uri);
        final String subTypeOfMIME = fileHandler.findSubTypeOfMIME();

        try {
            final byte[] fileData = fileHandler.read();

            return new Response.Builder(StatusLine.OK_200)
                    .contentType(subTypeOfMIME)
                    .contentLength(fileData)
                    .build();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("500에러");
        }
    }
}
