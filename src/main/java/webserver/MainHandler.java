package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequestFactory;
import webserver.request.body.HttpRequestBody;
import webserver.request.line.HttpRequestLine;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

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
            final List<String> requestMessage = getRequestMessage(in);
            final HttpRequestFactory factory = new HttpRequestFactory(requestMessage);
            final HttpRequestLine requestLine = factory.createRequestLine();

            if (requestLine.isPOST()) {
                HttpRequestBody requestBody = factory.createRequestBody();
            }

            final File file = requestLine.execute();
            final Response response = new Response(file);

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

    private List<String> getRequestMessage(final InputStream in) throws IOException{
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        final String requesLine = br.readLine();
        return br.lines()
                .collect(Collectors.toList());

        //lines.forEach(line -> logger.debug("라인 : {}", line));
//        String line;
//        while (!(line = br.readLine()).isEmpty()) {
//            logger.debug("헤더필드 : {}", line);
//        }
     //   return requesLine;
    }
}
