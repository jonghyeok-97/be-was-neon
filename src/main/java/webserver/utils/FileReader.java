package webserver.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

    public static byte[] read(final File file) throws IOException {
        final byte[] fileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(fileDatas);
        }
        return fileDatas;
    }
}
