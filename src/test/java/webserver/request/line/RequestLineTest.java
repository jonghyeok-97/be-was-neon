package webserver.request.line;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @Test
    @DisplayName("POST일 때, 읽을 파일을 못 찾는다")
    void notFindFileWhenPOST() {
        final RequestLine line = new RequestLine("POST /index.html HTTP1.1");
        Optional<File> optionalFile = line.findFile();

        assertThat(optionalFile.isPresent()).isFalse();
    }

    @Test
    @DisplayName("GET일 때, 읽을 파일을 찾는다")
    void findFileWhenGet() {
        final RequestLine line = new RequestLine("GET /index.html HTTP1.1");
        Optional<File> optionalFile = line.findFile();

        assertThat(optionalFile.isPresent()).isTrue();
    }
}