package webserver.handler;

import http.MIME;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    public static final String DIRECTORY = "src/main/resources/static";
    private final File file;

    FileHandler(final String path) {
        this.file = new File(DIRECTORY + path);
        if (!file.exists()) {
            throw new IllegalArgumentException("404에러");
        }
    }

    byte[] read() {
        final byte[] data = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(data);
            return data;
        } catch (IOException e) {
            throw new IllegalArgumentException("500에러");
        }
    }

    String findSubTypeOfMIME() {
        final int extensionPosition = file.getName().lastIndexOf(".");
        final String fileType = file.getName().substring(extensionPosition);
        return MIME.find(fileType).getSubType();
    }
}
