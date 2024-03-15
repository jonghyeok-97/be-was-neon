package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileRequest implements Request {
    private static final Logger logger = LoggerFactory.getLogger(FileRequest.class);
    private static final String RESOURCES_BASE_PATH = "src/main/resources/static";
    private final String URI;

    // /index.html 란 URL이 들어오면 /static/index.html 을 응답.
    private FileRequest(final String URI) {
        this.URI = URI;
    }

    public static FileRequest from(final String URI) {
        return new FileRequest(URI);
    }

    public static FileRequest fromDefaultURI() {
        return new FileRequest("/index.html");
    }

    public Response execute() throws IOException {
        final File file = findFile();
        final String extension = getExtension(file);
        final byte[] fileDatas = readFile(file);
        return new Response(fileDatas, extension);
    }

    public Response getBaseHtmlFile() throws IOException{
        final File baseFile = new File(RESOURCES_BASE_PATH + URI);
        final String extension = getExtension(baseFile);
        return new Response(readFile(baseFile), extension);
    }

    private String getExtension(final File file) {
        final String fileName = file.getPath();
        final int extensionPosition = fileName.lastIndexOf('.');
        return fileName.substring(extensionPosition+1);
    }

    private File findFile() {
        final List<File> files = new ArrayList<>();
        final File baseDir = new File(RESOURCES_BASE_PATH);
        // static 폴더 아래의 폴더 및 파일들을 재귀적으로 순회하면서 모든 .html 파일을 찾는다.
        searchFile(baseDir, files);

        return files.stream()
                .filter(file -> file.getPath().contains(URI.replace("/", "\\")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("파일을 못찾아요~"));
    }

    private void searchFile(File dir, List<File> files) {
        File[] subDirectorys = dir.listFiles();
        for (File sub : subDirectorys) {
            if (sub.isDirectory()) {
                searchFile(sub, files);
            } else if (sub.isFile() && hasExtension(sub.getName())) {
                files.add(sub);
            }
        }
    }

    private boolean hasExtension(final String fileName) {
        return fileName.endsWith(".html") || fileName.endsWith(".css") || fileName.endsWith(".ico")
                || fileName.endsWith(".svg") || fileName.endsWith(".png") || fileName.endsWith(".jpg")
                || fileName.endsWith(".js") || fileName.endsWith(".jpeg");
    }

    private byte[] readFile(final File file) throws IOException{
        final byte[] htmlFileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(htmlFileDatas);
        }
        return htmlFileDatas;
    }
}
