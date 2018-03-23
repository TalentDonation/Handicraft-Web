package com.handicraft.api.controller;

import com.handicraft.api.service.AuthService;
import com.handicraft.core.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTests {

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @Autowired
    private TestRestTemplate restTemplate;

}
