package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class HtmlRequest {
    private static final Logger logger = LoggerFactory.getLogger(HtmlRequest.class);
    private static final String BASE_DIR = "src/main/resources/static";
    private final String url;

    // /index.html -> /static/index.html
    // /register.html 와 /registration/index.html -> static/registration/index.html
    // htmlName 은 <index.html> , <register.html> , <registration.html> 중 하나
    public HtmlRequest(final String url) {
        this.url = url;
    }

    public Response execute() throws IOException {
        // <index.html> 과 <register.html> 인지 찾기
        final DirectoryPath path = DirectoryPath.findFrom(url);
        final File file = decide(path);

        final byte[] htmlFileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(htmlFileDatas);
        }
        return new Response(htmlFileDatas);
    }

    // index.html 과 register.html 을 못찾으면 static 디렉토리를 한개씩 열어보며 URL 과 일치하는 .html 파일을 찾기
    // URL은 /registration 과 /registraion/index.html 이 들어옴 -> registration.html 을 찾게 됨.
    private File decide(final DirectoryPath path) {
        if (path == DirectoryPath.NONE) {
            return findHTMLFile();
        }
        return new File(path.relativePath);
    }

    private File findHTMLFile() {
        final List<File> files = new ArrayList<>();
        final File dir = new File(BASE_DIR);
        searchHTMLFile(dir, files);

        return files.stream()
                .filter(file -> file.getPath().contains(url.replace("/", "\\")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("파일을 못찾아요~"));
    }

    private void searchHTMLFile(File dir, List<File> files) {
        File[] underDir = dir.listFiles();
        for (File file : underDir) {
            if (file.isDirectory()) {
                searchHTMLFile(file, files);
            } else if (file.isFile() && file.getName().endsWith(".html")) {
                files.add(file);
            }
        }
    }

    private enum DirectoryPath {
        STATIC("/index.html", BASE_DIR + "/index.html"),
        REGISTRATION("/register.html", BASE_DIR + "/registration/index.html"),
        NONE("", "");

        private final String fileName;
        private final String relativePath;

        DirectoryPath(final String fileName, final String relativePath) {
            this.fileName = fileName;
            this.relativePath = relativePath;
        }

        private static DirectoryPath findFrom(final String fileName) {
            return Stream.of(values())
                    .filter(path -> path.fileName.equals(fileName))
                    .findFirst()
                    .orElse(NONE);
        }
    }
}
