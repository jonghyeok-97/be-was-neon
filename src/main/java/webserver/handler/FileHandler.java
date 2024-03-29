package webserver.handler;

import http.MIME;
import http.requestMessage.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    byte[] makeDynamicHTMLWith(final String userName) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            final StringBuilder html = changeToDynamicHTML(br, userName);
            return html.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("500에러");
        }
    }

    private StringBuilder changeToDynamicHTML(BufferedReader br, final String userName) throws IOException{
        final StringBuilder html = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("account")) {
                html.append(line.replace("account", userName));
                continue;
            }
            html.append(line);
        }
        return html;
    }
}
