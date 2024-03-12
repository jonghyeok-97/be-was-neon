package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherFileTest {
    private DispatcherFile dispatcherFile;

    @BeforeEach
    void setUp() {
        dispatcherFile = new DispatcherFile();
    }

    @Test
    @DisplayName("HTTP 요청의 첫번째 라인에서 파일 이름을 찾는다.")
    void getFileNameFromHttpRequestFirstLine() {
        final String fileName = dispatcherFile.getFileName("GET /index.html v.v1");

        assertThat(fileName).isEqualTo("index.html");
    }

    @Test
    @DisplayName("파일 이름을 가지고 파일을 찾는다")
    void findFileWithFileName() {
        final String fileName = "index.html";

        final File actual = dispatcherFile.findFile(fileName);
        final File expected = new File(fileName);

        assertThat(actual.getName()).isEqualTo(expected.getName());
    }
}