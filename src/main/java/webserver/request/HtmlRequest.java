package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class HtmlRequest implements Request {
    private static final Logger logger = LoggerFactory.getLogger(HtmlRequest.class);
    private static final String BASE_DIR = "src/main/resources/static";
    private final String url;

    // /index.html 란 URL이 들어오면 /static/index.html 을 응답.
    // /register.html 와 /registration/index.html 와 registration.html 란 URL이 들어오면 static/registration/index.html 을 응답해야 함.
    private HtmlRequest(final String url) {
        this.url = url;
    }

    public static HtmlRequest from(final String url) {
        return new HtmlRequest(url);
    }

    public static HtmlRequest fromDefaultURL() {
        return new HtmlRequest("");
    }

    public Response execute() throws IOException {
        // <index.html> 과 <register.html> 인지 찾기
        final DirectoryPath path = DirectoryPath.findFrom(url);
        final File file = decide(path);
        final byte[] fileDatas = readFile(file);

        return new Response(fileDatas);
    }

    public byte[] getBaseHtmlFile() throws IOException{
        final File baseFile = new File(BASE_DIR + DirectoryPath.STATIC.fileName);
        return readFile(baseFile);
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
        final File baseDir = new File(BASE_DIR);
        // static 폴더 아래의 폴더 및 파일들을 재귀적으로 순회하면서 모든 .html 파일을 찾는다.
        searchHTMLFile(baseDir, files);

        return files.stream()
                .filter(file -> file.getPath().contains(url.replace("/", "\\")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("파일을 못찾아요~"));
    }

    private void searchHTMLFile(File dir, List<File> files) {
        File[] subDirectorys = dir.listFiles();
        for (File sub : subDirectorys) {
            if (sub.isDirectory()) {
                searchHTMLFile(sub, files);
            } else if (sub.isFile() && sub.getName().endsWith(".html")) {
                files.add(sub);
            }
        }
    }

    private byte[] readFile(final File file) throws IOException{
        final byte[] htmlFileDatas = new byte[(int) file.length()];
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.read(htmlFileDatas);
        }
        return htmlFileDatas;
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
