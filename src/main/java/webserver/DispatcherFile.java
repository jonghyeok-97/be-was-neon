package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class DispatcherFile {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherFile.class);
    private static final String LINE_PARSER = " ";
    private static final String DIR_RELATIVE_PATH = "src/main/resources/static";

    public String getFileName(final String requestFirstLine) {
        final String[] parsedLine = requestFirstLine.split(LINE_PARSER);
        final String fileName = parsedLine[1];
        return fileName;
    }

    public File findFile(final String fileName) {
        logger.debug("파일 이름 : {}", fileName);
        final String filePath = DIR_RELATIVE_PATH + fileName;
        return new File(filePath);
    }
}
