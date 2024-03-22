package webserver.handler;

import webserver.request.Request;
import webserver.response.MIME;
import webserver.response.Response;
import webserver.response.StatusLine;
import webserver.utils.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class GetHandler implements Handler{
    private final Request request;

    GetHandler(final Request request) {
        this.request = request;
    }

    public Response handle() throws IOException {
        final Optional<File> optFile = request.findFile();
        final MIME mime = optFile.map(file -> {
            final int extensionPosition = file.getName().lastIndexOf(".");
            final String fileType = file.getName().substring(extensionPosition);
            return MIME.find(fileType);
        }).orElseThrow(() -> new IllegalArgumentException("404에러"));

        byte[] data = new byte[0];
        if (optFile.isPresent()) {
            data = FileReader.read(optFile.get());
        }
        return new Response.Builder(StatusLine.OK.getValue())
                .contentType(mime.getSubType())
                .contentLength(data)
                .build();
    }
}
