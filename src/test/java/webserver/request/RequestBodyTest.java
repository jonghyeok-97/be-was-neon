package webserver.request;

import db.Database;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyTest {

    @Test
    @DisplayName("request body를 이용해서 사용자를 DB에 1개 추가한다.")
    void addUserToDB() {
        final RequestBody body = new RequestBody("user=종혁&password=123&name=Gromit");

      //  body.addUserToDB();

        Assertions.assertThat(Database.findAll().size()).isEqualTo(1);
    }
}