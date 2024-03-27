package webserver.handler;

import http.MIME;
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

    public static boolean isExistFile(final String uri) {
        final File file = new File(BasicPath.BASE.getPath() + uri);
        return file.exists();
    }

    public static FileHandler createFileHandler(final String uri) {
        return new FileHandler(new File(BasicPath.BASE.getPath() + uri));
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
