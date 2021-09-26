package com.example.chapter4.demo.user;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    public static final String SERVICE_HOST = "http://localhost:8080/v1/me";
    private RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getAuthenticatedUser() {
        return this.restTemplate.getForObject(SERVICE_HOST, User.class);
    }

}
