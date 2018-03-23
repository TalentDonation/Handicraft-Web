package com.handicraft.api.controller;

import com.handicraft.api.service.AuthService;
import com.handicraft.core.domain.User;
import com.handicraft.core.dto.FurnitureDto;
import com.handicraft.core.dto.UserDto;
import com.handicraft.core.service.FurnitureService;
import com.handicraft.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FurnitureService furnitureService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {

    }

    @Test
    public void shouldFindAll(){
        // given
        int tryCnt = 10;
        User user = null;
        for(int i = 1 ; i <= tryCnt ; ++i) {
            user = userService.create(toUser(i));
        }

        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        HttpEntity httpEntity = new HttpEntity(headers);

        // when
        ResponseEntity<List> responseEntity = restTemplate.exchange("/users", HttpMethod.GET, httpEntity, List.class);
        List<UserDto> users = responseEntity.getBody();

        // then
        assertThat(users).isNotNull();
        assertThat(users.size()).isGreaterThanOrEqualTo(tryCnt);
    }

    @Test
    public void shouldFindUser() {
        // given
        long uid = 111;
        User user = userService.create(toUser(uid));
        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<UserDto> responseEntity = restTemplate.exchange("/users/" + uid, HttpMethod.GET, httpEntity, UserDto.class);
        UserDto result = responseEntity.getBody();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUid()).isEqualTo(user.getUid());
        assertThat(result.getAddress()).isEqualTo(user.getAddress());
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getName()).isEqualTo(user.getName());
        assertThat(result.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(result.getJoinAt()).isEqualTo(user.getJoinAt());
        assertThat(result.getAvatar()).isNull();
    }

    @Test
    public void shouldFindUserNotFound() {
        // given
        long uid = 111;
        User user = userService.create(toUser(uid));
        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        HttpEntity httpEntity = new HttpEntity(headers);

        long badUid = 222;
        ResponseEntity<UserDto> responseEntity = restTemplate.exchange("/users/" + badUid, HttpMethod.GET, httpEntity, UserDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldUpdateUser() {
        // given
        long uid = 111;
        User user = userService.create(toUser(uid));
        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        UserDto userDto = new UserDto(user);
        userDto.setAddress(user.getAddress() + " update");
        userDto.setBirthday(user.getBirthday() + " update");
        userDto.setNickname(user.getNickname() + " update");
        userDto.setPhone(user.getPhone() + " update");
        userDto.setName(user.getName() + " update");
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(userDto, headers);

        ResponseEntity<UserDto> responseEntity = restTemplate.exchange("/users/" + uid, HttpMethod.PUT, httpEntity, UserDto.class);
        UserDto result = userService.findOne(uid);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(result).isNotNull();
        assertThat(result.getUid()).isEqualTo(userDto.getUid());
        assertThat(result.getNickname()).isEqualTo(userDto.getNickname());
        assertThat(result.getAddress()).isEqualTo(userDto.getAddress());
        assertThat(result.getName()).isEqualTo(userDto.getName());
        assertThat(result.getBirthday()).isEqualTo(userDto.getBirthday());
        assertThat(result.getJoinAt()).isEqualTo(userDto.getJoinAt());
        assertThat(result.getAvatar()).isNull();
    }

    @Test
    public void shouldUpdateUserNotFound() {
        // given
        long uid = 111;
        User user = userService.create(toUser(uid));
        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        UserDto userDto = new UserDto(user);
        userDto.setUid(222);
        userDto.setAddress(user.getAddress() + " update");
        userDto.setBirthday(user.getBirthday() + " update");
        userDto.setNickname(user.getNickname() + " update");
        userDto.setPhone(user.getPhone() + " update");
        userDto.setName(user.getName() + " update");
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(userDto, headers);

        ResponseEntity<UserDto> responseEntity = restTemplate.exchange("/users/" + uid, HttpMethod.PUT, httpEntity, UserDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldRemoveUser() {
        // given
        long uid = 111;
        User user = userService.create(toUser(uid));
        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserDto> responseEntity = restTemplate.exchange("/users/" + uid, HttpMethod.DELETE, httpEntity, UserDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void shouldRemoveUserNotFound() {
        // given
        long uid = 111;
        User user = userService.create(toUser(uid));
        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(headers);

        long badUid = 222;
        ResponseEntity<UserDto> responseEntity = restTemplate.exchange("/users/" + badUid, HttpMethod.DELETE, httpEntity, UserDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    // TODO: 2018. 3. 21. Add avatar test code

    @Test
    public void shouldFindFurniture() {
        // given
        long uid = 111;
        long fid = 222;
        User user = userService.create(toUser(uid));
        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        int tryCnt = 9;
        for(int cnt = 0; cnt < tryCnt; ++cnt) {
            System.out.println(fid + cnt);
            FurnitureDto furnitureDto = toFurnitureDto(fid + cnt);
            furnitureService.create(furnitureDto);
        }

        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<List> responseEntity = restTemplate.exchange("/users/" + uid + "/furniture", HttpMethod.GET, httpEntity, List.class);
        List<FurnitureDto> furnitureDtos = responseEntity.getBody();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(furnitureDtos.size()).isGreaterThanOrEqualTo(tryCnt);
    }

    @Test
    public void shouldCreateFurniture() {
        // given
        long uid = 111;
        long fid = 222;
        User user = userService.create(toUser(uid));
        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        FurnitureDto furnitureDto = toFurnitureDto(fid);
        HttpEntity<FurnitureDto> httpEntity = new HttpEntity<>(furnitureDto, headers);

        ResponseEntity<FurnitureDto> responseEntity = restTemplate.exchange("/users/" + uid + "/furniture", HttpMethod.POST, httpEntity, FurnitureDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void shouldNotFoundCreateFurniture() {
        // given
        long uid = 111;
        long fid = 222;
        User user = userService.create(toUser(uid));
        Optional<String> token = authService.createToken(user.getUid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "craft " + token.get());
        FurnitureDto furnitureDto = toFurnitureDto(fid);
        HttpEntity<FurnitureDto> httpEntity = new HttpEntity<>(furnitureDto, headers);

        long badUid = 222;
        ResponseEntity<FurnitureDto> responseEntity = restTemplate.exchange("/users/" + badUid + "/furniture", HttpMethod.POST, httpEntity, FurnitureDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }



    private User toUser(long uid) {
        User user = new User();
        user.setUid(uid);
        user.setName("name " + uid);
        user.setPhone("phone " + uid);
        user.setNickname("nickname "+ uid);
        user.setBirthday("birthday " + uid);
        user.setAddress("address " + uid);
        user.setJoinAt(ZonedDateTime.now());
        return user;
    }

    private FurnitureDto toFurnitureDto(long fid) {
        FurnitureDto furnitureDto = new FurnitureDto();
        furnitureDto.setFid(fid);
        furnitureDto.setBrand("brand " + fid);
        furnitureDto.setClosed(true);
        furnitureDto.setCreateAt(ZonedDateTime.now());
        furnitureDto.setDescription("description " + fid);
        furnitureDto.setGrade(String.valueOf(fid));
        furnitureDto.setHeight(Double.valueOf(fid));
        furnitureDto.setLength(Double.valueOf(fid));
        furnitureDto.setLocation("location " + fid);
        furnitureDto.setPeriodOfUse(String.valueOf(fid));
        furnitureDto.setPrice(String.valueOf(fid));
        furnitureDto.setWidth(Double.valueOf(fid));
        furnitureDto.setSellerKakao("kakao " + fid);
        furnitureDto.setUpdateAt(ZonedDateTime.now());
        furnitureDto.setSellerPhone("phone " + fid);
        furnitureDto.setType("type " + fid);
        furnitureDto.setTitle("title "+ fid);
        furnitureDto.setState("state "+ fid);
        return furnitureDto;
    }
}
