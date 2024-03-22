package webserver.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class FileReader {

    private FileReader(){}

    public static byte[] read(final File file) throws IOException {
        Objects.requireNonNull(file, file + "을 못 읽습니다.");
        final byte[] fileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(fileDatas);
        }
        return fileDatas;
    }
}
