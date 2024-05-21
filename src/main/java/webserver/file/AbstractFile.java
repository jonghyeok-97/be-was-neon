package webserver.file;

import http.MIME;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class AbstractFile {
    private static final String DIRECTORY = "src/main/resources/static";

    private final File file;

    public AbstractFile(String uri) {
        this.file = new File(DIRECTORY + uri);
        if (!file.exists()) {
            throw new IllegalArgumentException("404에러");
        }
    }

    public String findSubTypeOfMIME() {
        final int extensionPosition = file.getName().lastIndexOf(".");
        final String fileType = file.getName().substring(extensionPosition);
        return MIME.find(fileType).getSubType();
    }

    public byte[] read() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            final StringBuilder html = readFile(br);
            return html.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    abstract protected StringBuilder readFile(BufferedReader br) throws IOException;
}
