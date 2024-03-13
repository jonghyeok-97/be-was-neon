package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class HttpResponse {
    private final static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final OutputStream out;

    public HttpResponse(final OutputStream out) {
        this.out = out;
    }

    public void createResponse(final File file) throws IOException {
        final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(out));
        final byte[] byteFileData = new byte[(int) file.length()];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(byteFileData);
        }
        response200Header(dos, byteFileData.length);
        responseBody(dos, byteFileData);
    }

    private void response200Header(final DataOutputStream dos, final int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(final DataOutputStream dos, final byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
