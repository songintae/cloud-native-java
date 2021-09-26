package com.example.chapter4.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.json-tests
 */
@JsonTest
class UserTest {
    private User user;

    @Autowired
    private JacksonTester<User> json;

    @BeforeEach
    void setUp() {
        User user = new User("user", "Jack", "Frost", "jfrost@example.com");
        user.setId(0L);
        user.setCreatedAt(12345L);
        user.setLastModified(12346L);
        this.user = user;
    }

    @Test
    void deserializeJson() throws IOException {
        String content = "{\n" +
                "  \"username\": \"user\",\n" +
                "  \"firstName\": \"Jack\",\n" +
                "  \"lastName\": \"Frost\",\n" +
                "  \"email\": \"jfrost@example.com\"\n" +
                "}";
        assertThat(this.json.parseObject(content).getUsername()).isEqualTo("user");
        assertThat(this.json.parseObject(content).getFirstName()).isEqualTo("Jack");
    }

    @Test
    void serializeJson() throws IOException {
        assertThat(this.json.write(user)).isEqualTo("user.json");
        assertThat(this.json.write(user)).isEqualToJson("user.json");
        assertThat(this.json.write(user)).hasJsonPathStringValue("@.username");

        assertJsonPropertyEquals("@.username", "user");
        assertJsonPropertyEquals("@.firstName", "Jack");
        assertJsonPropertyEquals("@.lastName", "Frost");
        assertJsonPropertyEquals("@.email", "jfrost@example.com");

    }

    private void assertJsonPropertyEquals(String key, String value) throws IOException {
        assertThat(this.json.write(user)).extractingJsonPathStringValue(key).isEqualTo(value);
    }
}