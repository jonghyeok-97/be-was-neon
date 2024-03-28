package http.requestMessage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MethodTest {

    @Test
    @DisplayName("메소드가 POST면 POST생성")
    void isPOST() {
        final Method method = new Method("POST");
        assertThat(method.isPOST()).isTrue();
    }

    @Test
    @DisplayName("메소드가 GET이면 GET생성")
    void isGET() {
        final Method method = new Method("GET");
        assertThat(method.isPOST()).isFalse();
    }
}