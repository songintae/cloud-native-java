package com.example.chapter4.demo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(UserService.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void getAuthenticatedUserShouldReturnUser() {
        this.server.expect(requestTo(UserService.SERVICE_HOST))
                .andRespond(withSuccess(new ClassPathResource("user.json", getClass()), MediaType.APPLICATION_JSON));

        User user = userService.getAuthenticatedUser();
        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getFirstName()).isEqualTo("Jack");
        assertThat(user.getLastName()).isEqualTo("Frost");
        assertThat(user.getEmail()).isEqualTo("jfrost@example.com");
    }

}