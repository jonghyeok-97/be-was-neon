package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final Logger logger = LoggerFactory.getLogger(Response200.class);
    private final File file;
    private final byte[] datas;

    // byte[]를 필드로 가지도록 해서 파일을 여러번 읽지 않도록 함.
    public Response200(final File file) throws IOException{
        this.file = file;
        this.datas = read(file);
    }

    // addNewLine은 Response란 인터페이스의 static 메서드
    @Override
    public String getHeader() {
        final StringBuilder header = new StringBuilder();
        header.append(addNewLine(StatusLine.OK.line))
                .append(addNewLine("Content-Type: " + findSubType()))
                .append(addNewLine(addNewLine("Content-Length: " + getBody().length)));
        return header.toString();
    }

    @Override
    public byte[] getBody() {
        return datas;
    }

    private byte[] read(final File file) throws IOException {
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
