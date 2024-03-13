package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpReader {
    private static final Logger logger = LoggerFactory.getLogger(HttpReader.class);

    public static String getPath(final InputStream in) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        final String oneLine = br.readLine();
        logger.debug("첫 줄 : {}",oneLine);
        return oneLine.split(" ")[1];
    }


}
