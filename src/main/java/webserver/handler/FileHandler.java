package webserver.handler;

import http.MIME;
import http.requestMessage.Uri;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class FileHandler {
    private static final String BASIC_PATH = "src/main/resources/static";
    private final File file;

    FileHandler(final Uri uri){
        Objects.requireNonNull(uri, uri + "는 널이 되면 안됨");
        this.file = uri.createFile(BASIC_PATH);
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
}
