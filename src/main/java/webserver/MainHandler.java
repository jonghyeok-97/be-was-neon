package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequestFactory;
import webserver.request.body.HttpRequestBody;
import webserver.request.line.HttpRequestLine;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            final HttpRequestFactory factory = new HttpRequestFactory(requestMessage);
            final HttpRequestLine requestLine = factory.createRequestLine();
            final Optional<HttpRequestBody> optRequestBody = factory.createRequestBody(requestLine);
            final HttpRequest httpRequest = new HttpRequest(requestLine, optRequestBody);


            final Response response = httpRequest.respond();

            final BufferedOutputStream bos = new BufferedOutputStream(out);
            final DataOutputStream dos = new DataOutputStream(bos);
            dos.writeBytes(response.getHeader());
            final byte[] bodyDatas = response.getBody();
            dos.write(bodyDatas, 0, bodyDatas.length);
            dos.flush();
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
}
