package webserver.response;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MIMETest {

    @Test
    @DisplayName("MIME에서 파일 타입에 따라 서브타입을 찾는다")
    void findSubType() {
        SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(MIME.findSubType(".html")).isEqualTo("text/html");
            softAssertions.assertThat(MIME.findSubType(".css")).isEqualTo("text/css");
            softAssertions.assertThat(MIME.findSubType(".svg")).isEqualTo("image/svg+xml");
            softAssertions.assertThat(MIME.findSubType(".ico")).isEqualTo("image/x-icon");
            softAssertions.assertThat(MIME.findSubType(".png")).isEqualTo("image/png");
            softAssertions.assertThat(MIME.findSubType(".jpg")).isEqualTo("image/jpg");
            softAssertions.assertThat(MIME.findSubType(".jpeg")).isEqualTo("image/jpeg");
            softAssertions.assertThat(MIME.findSubType("")).isEqualTo("");
                }
        );
    }
}