package webserver.request;

import db.Database;
import model.UserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class RequestBodyTest {

    @Test
    @DisplayName("request body를 가지고 유저의 이름,패스워드,닉네임을 얻는다.")
    void addUserToDB() {
        final RequestBody body = new RequestBody("userId=jonghyeok&password=123&name=Gromit");

        final Optional<String> optUserId = body.findValueFrom(UserInfo.USER_ID);
        final Optional<String> optPassword = body.findValueFrom(UserInfo.PASSWORD);
        final Optional<String> optName = body.findValueFrom(UserInfo.NICKNAME);

        Assertions.assertThat(optUserId).isNotEmpty();
        Assertions.assertThat(optPassword).isNotEmpty();
        Assertions.assertThat(optName).isNotEmpty();
    }
}