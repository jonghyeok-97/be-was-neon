package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilePathTest {

    @Test
    @DisplayName("HTTP 요청의 첫번째 라인을 \" \" 로 분리해서 파일 이름을 찾는다.")
    void getFileNameFromHttpFirstLine() {
        final String httpRequestFirstLine = "GET /index.html v.v1";
        final String fileName = FilePath.getFileName(httpRequestFirstLine);

        Assertions.assertThat(fileName).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("/index.html 가 있는 상대 경로를 찾는다")
    void findFilePath() {
        final String filePath = FilePath.findPath("/index.html");
        final String expectedPath = "src/main/resources/static/index.html";

        Assertions.assertThat(filePath).isEqualTo(expectedPath);
    }
}