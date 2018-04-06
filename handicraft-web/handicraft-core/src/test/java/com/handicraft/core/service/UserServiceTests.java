package com.handicraft.core.service;

import com.handicraft.core.domain.User;
import com.handicraft.core.dto.UserDto;
import com.handicraft.core.repository.UserRepository;
import com.handicraft.core.support.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void shouldFindAll() {
        // given
        int tryCnt = 10;
        List<User> users = new LinkedList<>();
        for (int i = 0; i < tryCnt; ++i) {
            users.add(toUser(i));
        }

        when(userRepository.findAll()).thenReturn(users);

        // when
        List<UserDto> userDtos = userService.findAll();

        // then
        assertThat(userDtos).isNotNull();
        assertThat(userDtos.size()).isEqualTo(tryCnt);
    }

    @Test
    public void shouldFindOne() {
        // given
        long userId = 1;
        User user = toUser(userId);

        when(userRepository.findOne(userId)).thenReturn(user);

        // when
        UserDto userDto = userService.findOne(userId);

        // then
        assertThat(userDto).isNotNull();
        assertThat(userDto.getUid()).isEqualTo(user.getUid());
        assertThat(userDto.getNickname()).isEqualTo(user.getNickname());
        assertThat(userDto.getName()).isEqualTo(user.getName());
        assertThat(userDto.getAddress()).isEqualTo(user.getAddress());
        assertThat(userDto.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(userDto.getJoinAt()).isEqualTo(user.getJoinAt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptUpdate() {
        // given
        long userId = 1;
        UserDto userDto = new UserDto();
        userDto.setUid(userId);
        when(userRepository.findOne(userId)).thenReturn(null);

        // when
        userService.update(userDto);
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
}
