package model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Testing Password BCrypt Encryption")
    void passwordEncryption() {
        String preComputedHash = "$2a$12$uMnpiKrEDuMoPzc8H6R9QOK7FFP6/zP4eAqh0F9f7f.Yg9FsNrsmm";
        String password = "inipassword123";
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), preComputedHash);
        assertTrue(result.verified, "Password Encryption Should Work");
    }

    @Test
    @DisplayName("Testing Register Function")
    void register() {
        User user = new User();
        assertDoesNotThrow(() -> user.register("testbug", "testbug", "testbug", "testbug"));
    }

    @Test
    @DisplayName("Testing Login Function")
    void login() {
        User user = new User();
        String username = "testbug";
        String password = "testbug";
        assertTrue(user.login(username, password), "Login Should Work");
    }

    @Test
    @DisplayName("Testing UpdateFunction")
    void update() {
        User user = new User();
        assertDoesNotThrow(() -> user.update(user.getIdByUsername("asep123"), "asep123"));
    }

    @Test
    @DisplayName("Testing DeleteFunction")
    void delete() {
        User user = new User();
        assertDoesNotThrow(() -> user.delete("testbug"));
    }

    @Test
    void getIdByUsername() {
        User user = new User();
        assertDoesNotThrow(() -> user.getIdByUsername("asep123"));
    }
}