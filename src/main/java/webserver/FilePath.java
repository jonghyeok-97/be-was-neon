package webserver;

public class FilePath {

    private static final String LINE_PARSER = " ";
    private static final String DIR_RELATIVE_PATH = "src/main/resources/static";

    public static String getFileName(final String requestFirstLine) {
        final String[] parsed = requestFirstLine.split(LINE_PARSER);
        final String fileName = parsed[1];
        return fileName;
    }

    public static String findPath(final String fileName) {
        final String filePath = DIR_RELATIVE_PATH + fileName;
        return filePath;
    }
}
