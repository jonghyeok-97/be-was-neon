package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.RequestHandler;
import webserver.request.Request;
import webserver.response.Response;

import java.io.*;
import java.net.Socket;

public class MainHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MainHandler.class);

    private final Socket connection;

    public MainHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
     }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final String requestMessage = getRequestMessage(in);

            final Request request = new Request(requestMessage);
            final RequestHandler handler = new RequestHandler(request);
            final Response response = handler.handle();

            final BufferedOutputStream bos = new BufferedOutputStream(out);
            final DataOutputStream dos = new DataOutputStream(bos);
            writeResponseMessage(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getRequestMessage(final InputStream in) throws IOException {
        final StringBuilder lines = new StringBuilder();
        do {
            lines.append((char) in.read());
        } while (in.available() > 0);
        return lines.toString();
    }

    private void writeResponseMessage(final DataOutputStream dos, final Response response) throws IOException {
        dos.writeBytes(response.getStartLine());
        dos.writeBytes(response.getHeader());
        dos.writeBytes(response.getEmptyLine());
        dos.write(response.getBody(), 0, response.getBody().length);
        dos.flush();
    }
}
