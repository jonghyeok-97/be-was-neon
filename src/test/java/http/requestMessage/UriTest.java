package http.requestMessage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UriTest {

    // ParameterizedTest 어노테이션이 왜 안되지..?
    @Test
    @DisplayName("URI에 해당하는 파일 이름이 있으면 URI 객체는 생성된다.")
    void createURI() {
        Assertions.assertThatCode(() -> new Uri("/favicon.ico"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("URI에 해당하는 파일 이름이 없으면 URI 객체는 생성되지 않는다.")
    void noCreateURI() {
        Assertions.assertThatThrownBy(() -> new Uri("/faviicon.ico"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}