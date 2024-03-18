package webserver.response;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static webserver.response.Response.*;

public class Response200 implements Response {

    private enum StatusLine {
        OK("HTTP/1.1 200 OK");

        private final String line;

        StatusLine(final String _line) {
            line = _line;
        }
    }

    private final File file;

    public Response200(final File file) {
        this.file = file;
    }

    public String getHeader() throws IOException{
        final StringBuilder header = new StringBuilder();
        header.append(addNewLine(StatusLine.OK.line))
                .append(addNewLine("Content-Type: " + findSubType()))
                .append(addNewLine(addNewLine("Content-Length: " + getBody().length)));

        return header.toString();
    }

    public byte[] getBody() throws IOException {
        final byte[] fileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(fileDatas);
        }
        return fileDatas;
    }

    private String findSubType() {
        final String path = file.getPath();
        final String mime = path.substring(path.lastIndexOf('.'));
        return MIME.findSubType(mime);
    }
}
