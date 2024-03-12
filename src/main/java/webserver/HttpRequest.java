package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final DispatcherFile dispatcherFile = new DispatcherFile();
    private final InputStream in;

    public HttpRequest(final InputStream in) {
        this.in = in;
    }

    public File getFile() throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        final String firstLine = br.readLine();
        final String fileName = dispatcherFile.getFileName(firstLine);
        return dispatcherFile.findFile(fileName);
    }
}
