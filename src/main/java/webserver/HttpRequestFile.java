package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.stream.Stream;

public class HttpRequestFile {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestFile.class);

    private final String line;

    // /index.html 이나 /register.html 처럼 들어옴.
    public HttpRequestFile(final String line) {
        this.line = line;
    }

    public Response execute() throws IOException {
        final String path = Path.getRelativePath(line);
        final File file = new File(path);
        final byte[] htmlFileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(htmlFileDatas);
        }
        return new Response(htmlFileDatas);
    }

    private enum Path {
        STATIC("index.html", "src/main/resources/static/index.html"),
        REGISTRATION("register.html", "src/main/resources/static/registration/index.html");

        private final String fileName;
        private final String relativePath;

        Path(final String fileName, final String relativePath) {
            this.fileName = fileName;
            this.relativePath = relativePath;
        }

        private static String getRelativePath(final String fileName) {
            return Stream.of(values())
                    .filter(path -> path.fileName.equals(fileName))
                    .map(path -> path.relativePath)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("404에러"));
        }
    }
}
