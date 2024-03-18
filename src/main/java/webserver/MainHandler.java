package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.Request;
import webserver.request.RequestFactory;
import webserver.request.body.RequestBody;
import webserver.request.line.RequestLine;
import webserver.response.Response;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

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
            final RequestFactory factory = new RequestFactory(requestMessage);

            final Request request = createRequest(factory);
            final Response response = request.respond();

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

    private Request createRequest(final RequestFactory factory) {
        final RequestLine requestLine = factory.createRequestLine();
        final Optional<RequestBody> optRequestBody = factory.createOptRequestBody(requestLine);
        return new Request(requestLine, optRequestBody);
    }

    private void writeResponseMessage(final DataOutputStream dos, final Response response) throws IOException {
        final byte[] bodyDatas = response.getBody();
        dos.writeBytes(response.getHeader());
        dos.write(bodyDatas, 0, bodyDatas.length);
        dos.flush();
    }
}
