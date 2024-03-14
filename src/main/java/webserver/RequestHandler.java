package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpReader;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            final String firstLine = HttpReader.getPath(in);
            final HttpRequestFile request = new HttpRequestFile(firstLine);

            final BufferedOutputStream bos = new BufferedOutputStream(out);
            final DataOutputStream dos = new DataOutputStream(bos);

            dos.writeBytes(response.get200Header());
            final byte[] bodyDatas = response.getBodyData();
            dos.write((bodyDatas), 0, bodyDatas.length);
            dos.flush();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
