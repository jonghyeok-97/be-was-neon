package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    @Test
    @DisplayName("헤더에 Content-Length 가 있으면 반환값에 값이 있다.")
    void getContentLengthWithHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "26");
        RequestHeader header = new RequestHeader(headers);

        Optional<Integer> contentLength = header.getContentLength();

        assertThat(contentLength).isNotEmpty();
    }

    @Test
    @DisplayName("헤더에 Content-Length 가 없으면 반환값에 값이 없다.")
    void getContentLengthWithNoHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Local:host", "8080");
        RequestHeader header = new RequestHeader(headers);

        Optional<Integer> contentLength = header.getContentLength();

        assertThat(contentLength).isEmpty();
    }
}