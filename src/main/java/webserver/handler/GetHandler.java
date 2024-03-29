package webserver.handler;

import http.requestMessage.Uri;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetHandler implements Handler {
    private static final Logger logger = LoggerFactory.getLogger(GetHandler.class);
    private final FileHandler fileHandler;

    GetHandler(final Uri uri) {
        this.fileHandler = new FileHandler(uri);
    }

    public Response handle() {
        final String subTypeOfMIME = fileHandler.findSubTypeOfMIME();
        final byte[] data = fileHandler.read();
        return new Response.Builder(StatusLine.OK_200)
                .contentType(subTypeOfMIME)
                .contentLength(data)
                .build();
    }
}
