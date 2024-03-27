package webserver.handler;

import http.MIME;
import http.requestMessage.Uri;
import webserver.path.BasicPath;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileHandler {
    private final File file;

    private FileHandler(final File file){
        this.file = file;
    }

    public static FileHandler createFileHandler(final Uri uri) {
        return new FileHandler(new File(BasicPath.BASE.getPath() + uri.getUri()));
    }

    public byte[] read() throws IOException {
        final byte[] fileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(fileDatas);
        }
        return fileDatas;
    }

    public String findSubTypeOfMIME() {
        final int extensionPosition = file.getName().lastIndexOf(".");
        final String fileType = file.getName().substring(extensionPosition);
        return MIME.find(fileType).getSubType();
    }
}
