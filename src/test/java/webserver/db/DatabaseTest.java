package webserver.db;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.model.User;

import java.util.Optional;

class DatabaseTest {

    @Test
    @DisplayName("로그인한 회원ID로 회원가입한 회원을 찾아낸다.")
    void findAll() {
        final User login = User.createUserForLogin("종혁", "123");
        final User register1 = User.createUserForLogin("종혁", "123");
        final User register2 = User.createUserForLogin("그로밋", "1");

        Database.addUser(register1);
        Database.addUser(register2);
        final String userId = login.getUserId();
        final Optional<User> found = Database.findUserById(userId);

        Assertions.assertThat(found).isNotEmpty();
    }
}