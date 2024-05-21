package webserver.handler;

import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.file.AbstractFile;

import java.io.IOException;

public class GetHandler implements Handler {
    private static final Logger logger = LoggerFactory.getLogger(GetHandler.class);

    private final AbstractFile abstractFile;

    public GetHandler(final AbstractFile abstractFile) {
        this.abstractFile = abstractFile;
    }

    @Override
    public Response start() {
        try {
            return new Response.Builder(StatusLine.OK_200)
                    .contentType(abstractFile.findSubTypeOfMIME())
                    .contentLength(abstractFile.read())
                    .build();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("500에러");
        }
    }
}