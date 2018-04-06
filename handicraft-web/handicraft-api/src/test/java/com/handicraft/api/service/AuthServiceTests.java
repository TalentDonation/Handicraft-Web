package com.handicraft.api.service;

import com.handicraft.core.domain.User;
import com.handicraft.core.repository.UserRepository;
import com.handicraft.core.support.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthServiceTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    @Test
    public void shouldFindProfile() {
        // given
        String accessToken = "TestAccessToken";
        String responseBody = "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"11111111\",\"nickname\":\"ko\",\"profile_image\":\"test\",\"age\":\"20-29\",\"gender\":\"M\",\"email\":\"email\",\"ci\":\"test\",\"realnm\":\"Y\",\"naverid\":\"kosb15\",\"name\":\"ko\",\"birthday\":\"1992-03-03\"}}";
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo("https://openapi.naver.com/v1/nid/me"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "Bearer " + accessToken))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON_UTF8));


        // when
        Optional<Map<String, String>> profile = authService.findProfile(accessToken);
        server.verify();

        // then
        assertThat(profile.isPresent()).isTrue();
        assertThat(profile.get().get("id")).isEqualTo("11111111");
        assertThat(profile.get().get("nickname")).isEqualTo("ko");
        assertThat(profile.get().get("profile_image")).isEqualTo("test");
        assertThat(profile.get().get("age")).isEqualTo("20-29");
        assertThat(profile.get().get("gender")).isEqualTo("M");
        assertThat(profile.get().get("email")).isEqualTo("email");
        assertThat(profile.get().get("naverid")).isEqualTo("kosb15");
        assertThat(profile.get().get("name")).isEqualTo("ko");
        assertThat(profile.get().get("birthday")).isEqualTo("1992-03-03");
    }

    @Test
    public void shouldCreateToken() throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        // given
        long userId = 1;

        User user = new User();
        user.setUid(userId);
        user.setName("name " + userId);
        user.setPhone("phone " + userId);
        user.setNickname("nickname " + userId);
        user.setBirthday("birthday " + userId);
        user.setAddress("address " + userId);
        user.setRole(Role.ADMIN.name());
        user.setJoinAt(ZonedDateTime.now());
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findOne(userId)).thenReturn(user);

        // when
        Optional<String> createdToken = authService.createToken(userId);

        // then
        assertThat(createdToken.isPresent()).isTrue();
        assertThat(createdToken.get()).isNotEmpty();
    }
}
