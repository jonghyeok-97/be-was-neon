package webserver.handler;

import http.MIME;
import http.requestMessage.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    private static final String DIRECTORY = "src/main/resources/static";
    private final File file;

    FileHandler(final Uri uri){
        Objects.requireNonNull(uri, uri + "는 널이 되면 안됨");
        this.file = new File(DIRECTORY + uri.getUri());
        if (!file.exists()) {
            throw new IllegalArgumentException("404에러");
        }
    }

    FileHandler(final String path) {
        this.file = new File(DIRECTORY + path);
        if (!file.exists()) {
            throw new IllegalArgumentException("404에러");
        }
    }

    byte[] read() throws IOException {
        final byte[] fileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(fileDatas);
        }
        return fileDatas;
    }

    String findSubTypeOfMIME() {
        final int extensionPosition = file.getName().lastIndexOf(".");
        final String fileType = file.getName().substring(extensionPosition);
        return MIME.find(fileType).getSubType();
    }

    byte[] showHTMLWith(final String userName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("account")) {
                    sb.append(line.replace("account", userName));
                    continue;
                }
                sb.append(line);
            }
            return sb.toString().getBytes();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("500에러");
        }

    }
}
