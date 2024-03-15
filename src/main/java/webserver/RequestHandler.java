package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpReader;
import webserver.request.FileRequest;
import webserver.request.RegisterRequest;
import webserver.request.Request;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.function.Function;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String REGISTER_URL = "create";
    private static final String HTML_URL = ".html";

    private final Socket connection;
    private final Map<String, Function<String, Request>> requestMapper;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.requestMapper = Map.ofEntries(
                Map.entry(REGISTER_URL, url -> new RegisterRequest(url)),
                Map.entry(HTML_URL, url -> FileRequest.from(url))
        );
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final String url = HttpReader.readURL(in);
            final Request request = find(url);
            final Response response = request.execute();

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

    private Request find(final String url) {
        return requestMapper.keySet().stream()
                .filter(url::contains)
                .map(urlType -> requestMapper.get(urlType).apply(url))
                .findFirst()
                .orElse(requestMapper.get(HTML_URL).apply(url));
    }
}
