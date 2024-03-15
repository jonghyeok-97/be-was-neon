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
    // /register.html 와 /registration/index.html 와 registration.html 란 URL이 들어오면 static/registration/index.html 을 응답해야 함.
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
        // <index.html> 과 <register.html> 인지 찾기
        final FilePath path = FilePath.findFrom(URI);
        final File file = decide(path);
        final String fileName = file.getPath();
        final int extensionPosition = fileName.lastIndexOf('.');
        final String extension = fileName.substring(extensionPosition+1);
        final byte[] fileDatas = readFile(file);

        return new Response(fileDatas, extension);
    }

    public Response getBaseHtmlFile() throws IOException{
        final File baseFile = new File(RESOURCES_BASE_PATH + FilePath.STATIC.fileName);
        return new Response(readFile(baseFile), "html");
    }

    // index.html 과 register.html 을 못찾으면 static 디렉토리를 한개씩 열어보며 URL 과 일치하는 .html 파일을 찾기
    // URL은 /registration 과 /registraion/index.html 이 들어옴 -> registration.html 을 찾게 됨.
    private File decide(final FilePath path) {
        if (path == FilePath.NONE) {
            return findFile();
        }
        return new File(path.relativePath);
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

    private enum FilePath {
        STATIC("/index.html", RESOURCES_BASE_PATH + "/index.html"),
        REGISTRATION("/register.html", RESOURCES_BASE_PATH + "/registration/index.html"),
        NONE("", "");

        private final String fileName;
        private final String relativePath;

        FilePath(final String fileName, final String relativePath) {
            this.fileName = fileName;
            this.relativePath = relativePath;
        }

        private static FilePath findFrom(final String fileName) {
            return Stream.of(values())
                    .filter(path -> path.fileName.equals(fileName))
                    .findFirst()
                    .orElse(NONE);
        }
    }
}
