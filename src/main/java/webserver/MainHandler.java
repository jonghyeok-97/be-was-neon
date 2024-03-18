package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.line.HttpRequestLine;

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
            final String line = getRequestLine(in);
            final HttpRequestLine requestLine = new HttpRequestLine(line);
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

    private String getRequestLine(final InputStream in) throws IOException{
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        final String requesLine = br.readLine();
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            logger.debug("헤더필드 : {}", line);
        }
        return requesLine;
    }
}
