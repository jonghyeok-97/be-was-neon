package webserver.response;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.MIME;

class MIMETest {

    @Test
    @DisplayName("MIME에서 파일 타입에 따라 서브타입을 찾는다")
    void findSubType() {
        SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(MIME.find(".html").getSubType()).isEqualTo("text/html");
                    softAssertions.assertThat(MIME.find(".css").getSubType()).isEqualTo("text/css");
                    softAssertions.assertThat(MIME.find(".svg").getSubType()).isEqualTo("image/svg+xml");
                    softAssertions.assertThat(MIME.find(".ico").getSubType()).isEqualTo("image/x-icon");
                    softAssertions.assertThat(MIME.find(".png").getSubType()).isEqualTo("image/png");
                    softAssertions.assertThat(MIME.find(".jpg").getSubType()).isEqualTo("image/jpg");
                    softAssertions.assertThat(MIME.find(".jpeg").getSubType()).isEqualTo("image/jpeg");
                }
        );
    }
}