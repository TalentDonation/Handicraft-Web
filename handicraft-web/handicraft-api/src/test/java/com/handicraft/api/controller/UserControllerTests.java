package com.handicraft.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.handicraft.core.domain.Avatar;
import com.handicraft.core.domain.User;
import com.handicraft.core.dto.FurnitureDto;
import com.handicraft.core.dto.UserDto;
import com.handicraft.core.service.FurnitureService;
import com.handicraft.core.service.UserService;
import com.handicraft.core.support.AESUtil;
import com.handicraft.core.support.Role;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc()
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private FurnitureService furnitureService;

    private static String token;
    private static final long USER_ID = 1;
    private static User USER;

    @BeforeClass
    public static void setUp() throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        USER = toUser(USER_ID);
        String secretKey = AESUtil.genererateRandomKey();
        token = AESUtil.encrypt(USER, secretKey);
        USER.setSecretKey(secretKey);
        USER.setToken(token);
    }

    @Test
    public void shouldFindAll() throws Exception {
        // given
        int cnt = 10;
        List<UserDto> userDtos = new LinkedList<>();
        for (int i = 0; i < cnt; ++i) {
            UserDto userDto = toUserDto(i);
            userDtos.add(userDto);
        }

        when(userService.loadUserByUsername(token)).thenReturn(USER);
        when(userService.findAll()).thenReturn(userDtos);


        // when
        MvcResult mvcResult = mockMvc.perform(get("/users").header("authorization", "craft " + token).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        // then
        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentLength()).isGreaterThanOrEqualTo(0);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    public void shouldFindUser() throws Exception {
        // given
        UserDto userDto = toUserDto(USER_ID);
        when(userService.loadUserByUsername(token)).thenReturn(USER);
        when(userService.findOne(USER_ID)).thenReturn(userDto);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}", USER_ID).header("authorization", "craft " + token).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value(userDto.getUid()))
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.phone").value(userDto.getPhone()))
                .andExpect(jsonPath("$.nickname").value(userDto.getNickname()))
                .andExpect(jsonPath("$.birthday").value(userDto.getBirthday()))
                .andExpect(jsonPath("$.address").value(userDto.getAddress()))
                .andExpect(jsonPath("$.joinAt").value(userDto.getJoinAt().toString()))
                .andReturn();


        // then
        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentLength()).isGreaterThanOrEqualTo(0);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isNotNull();

    }


    @Test
    public void shouldUpdateUser() throws Exception {
        // given
        UserDto userDto = toUserDto(USER_ID);
        when(userService.loadUserByUsername(token)).thenReturn(USER);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        String userDtoToString = objectMapper.writeValueAsString(userDto);

        // when
        MvcResult mvcResult = mockMvc.perform(put("/users/{userId}", USER_ID).header("authorization", "craft " + token).contentType(MediaType.APPLICATION_JSON_UTF8).content(userDtoToString))
                .andExpect(status().isOk())
                .andReturn();

        // then
        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentLength()).isEqualTo(0);
        assertThat(response.getContentAsString()).isNotNull();
    }


    @Test
    public void shouldRemoveUser() throws Exception {
        // given
        UserDto userDto = toUserDto(USER_ID);
        when(userService.loadUserByUsername(token)).thenReturn(USER);

        // when
        MvcResult mvcResult = mockMvc.perform(delete("/users/{userId}", USER_ID).header("authorization", "craft " + token).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        // then
        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentLength()).isEqualTo(0);
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    public void shouldFindUserAvatar() throws Exception {
        // given
        long gid = 1;
        Avatar avatar = toAvatar(gid);
        when(userService.loadUserByUsername(token)).thenReturn(USER);
        when(userService.findAvatar(USER_ID)).thenReturn(avatar);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}/avatar", USER_ID).header("authorization", "craft " + token).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gid").value(avatar.getGid()))
                .andExpect(jsonPath("$.name").value(avatar.getName()))
                .andExpect(jsonPath("$.size").value(avatar.getSize()))
                .andExpect(jsonPath("$.extension").value(avatar.getExtension()))
                .andExpect(jsonPath("$.updateAt").value(avatar.getUpdateAt().toString()))
                .andExpect(jsonPath("$.createAt").value(avatar.getCreateAt().toString()))
                .andReturn();


        // then
        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentLength()).isGreaterThanOrEqualTo(0);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    public void shouldCreateUserAvatar() throws Exception {
        // given
        when(userService.loadUserByUsername(token)).thenReturn(USER);
        MockMultipartFile file = new MockMultipartFile("file", "test.png", MediaType.IMAGE_PNG_VALUE, "FileUploadTest".getBytes("UTF8"));
        when(userService.storeAvatar(USER_ID, file)).thenReturn(101L);

        // when
        MvcResult mvcResult = mockMvc.perform(fileUpload("/users/{uid}/avatar", USER_ID)
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("authorization", "craft " + token))
                .andExpect(status().isCreated())
                .andReturn();


        // then
        MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentLength()).isGreaterThanOrEqualTo(0);
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    public void shouldFindUserFurniture() throws Exception {
        // given
        int tryCnt = 10;
        when(userService.loadUserByUsername(token)).thenReturn(USER);
        List<FurnitureDto> furnitureDtos = new LinkedList<>();
        for (int i = 0; i < tryCnt; ++i) {
            furnitureDtos.add(toFurnitureDto(i));
        }

        when(furnitureService.find(USER_ID, 0, 10)).thenReturn(furnitureDtos);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // when
        MvcResult mvcResult = mockMvc.perform(get("/users/{uid}/furniture", USER_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("authorization", "craft " + token))
                .andExpect(status().isOk())
                .andReturn();


        // then
        MockHttpServletResponse response = mvcResult.getResponse();
        List furnitureDtoList = objectMapper.readValue(response.getContentAsString(), List.class);
        assertThat(response.getContentLength()).isGreaterThanOrEqualTo(0);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(furnitureDtoList).isNotNull();
        assertThat(furnitureDtoList.size()).isEqualTo(tryCnt);
    }


    private static User toUser(long uid) {
        User user = new User();
        user.setUid(uid);
        user.setName("name " + uid);
        user.setPhone("phone " + uid);
        user.setNickname("nickname " + uid);
        user.setBirthday("birthday " + uid);
        user.setAddress("address " + uid);
        user.setRole(Role.ADMIN.name());
        user.setJoinAt(ZonedDateTime.now());
        user.setAccountExpired(false);
        user.setAccountLocked(false);
        user.setEnabled(true);
        return user;
    }

    private static UserDto toUserDto(long uid) {
        UserDto userDto = new UserDto();
        userDto.setUid(uid);
        userDto.setName("name " + uid);
        userDto.setPhone("phone " + uid);
        userDto.setNickname("nickname " + uid);
        userDto.setBirthday("birthday " + uid);
        userDto.setAddress("address " + uid);
        userDto.setJoinAt(ZonedDateTime.now());
        return userDto;
    }

    private static FurnitureDto toFurnitureDto(long fid) {
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
        furnitureDto.setTitle("title " + fid);
        furnitureDto.setState("state " + fid);
        return furnitureDto;
    }

    private static Avatar toAvatar(long gid) {
        Avatar avatar = new Avatar();
        avatar.setSize(gid);
        avatar.setName("name " + gid);
        avatar.setExtension(".png");
        avatar.setGid(gid);
        avatar.setUpdateAt(ZonedDateTime.now());
        avatar.setCreateAt(ZonedDateTime.now());
        return avatar;
    }
}
