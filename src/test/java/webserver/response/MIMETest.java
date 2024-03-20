package webserver.response;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MIMETest {

    @Test
    @DisplayName("MIME에서 파일 타입에 따라 서브타입을 찾는다")
    void findSubType() {
        SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(MIME.find(".html")).isEqualTo("text/html");
            softAssertions.assertThat(MIME.find(".css")).isEqualTo("text/css");
            softAssertions.assertThat(MIME.find(".svg")).isEqualTo("image/svg+xml");
            softAssertions.assertThat(MIME.find(".ico")).isEqualTo("image/x-icon");
            softAssertions.assertThat(MIME.find(".png")).isEqualTo("image/png");
            softAssertions.assertThat(MIME.find(".jpg")).isEqualTo("image/jpg");
            softAssertions.assertThat(MIME.find(".jpeg")).isEqualTo("image/jpeg");
            softAssertions.assertThat(MIME.find("")).isEqualTo("");
                }
        );
    }
}