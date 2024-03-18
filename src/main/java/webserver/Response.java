package webserver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Response {
    private final File file;

    public Response(final File file) {
        this.file = file;
    }

    public String getHeader() throws IOException{
        final StringBuilder header = new StringBuilder();
        header.append(addNewLine("HTTP/1.1 200 OK "))
                .append(addNewLine("Content-Type: " + findSubType()))
                .append(addNewLine(addNewLine("Content-Length: " + getBody().length)));

        return header.toString();
    }

    private String addNewLine(final String line) {
        return line + "\r\n";
    }

    private String findSubType() {
        final String path = file.getPath();
        final String mime = path.substring(path.lastIndexOf('.'));
        return MIME.findSubType(mime);
    }

    public byte[] getBody() throws IOException {
        final byte[] fileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(fileDatas);
        }
        return fileDatas;
    }
}
