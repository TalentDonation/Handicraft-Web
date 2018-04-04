package com.handicraft.core.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.handicraft.core.domain.Avatar;
import com.handicraft.core.domain.User;
import com.handicraft.core.dto.UserDto;
import com.handicraft.core.repository.AvatarRepository;
import com.handicraft.core.repository.UserRepository;
import com.handicraft.core.support.FileModule;
import com.handicraft.core.support.HashUtil;
import com.handicraft.core.support.Role;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;

    @Autowired
    public UserService(UserRepository userRepository, AvatarRepository avatarRepository) {
        this.userRepository = userRepository;
        this.avatarRepository = avatarRepository;
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            UserDto userDto = new UserDto(user);
            userDto.makeAvatar(user.getAvatar());
            userDtos.add(userDto);
        });

        return userDtos;
    }

    public UserDto findOne(long uid) {
        if (!userRepository.exists(uid))
            throw new IllegalArgumentException();

        User user = userRepository.findOne(uid);
        UserDto userDto = new UserDto(user);
        userDto.makeAvatar(user.getAvatar());
        return userDto;
    }

    public User findOneByFileName(String avatarName) {
        User user = userRepository.findByAvatarName(avatarName);
        if (user == null) {
            throw new IllegalArgumentException();
        }

        return user;
    }

    public Avatar findAvatar(long uid) {
        User user = userRepository.findOne(uid);
        if (user == null || user.getAvatar() == null) {
            throw new IllegalArgumentException();
        }

        return user.getAvatar();
    }

    public User create(UserDto userDto) {
        User user = new User(userDto);
        return userRepository.saveAndFlush(user);
    }

    public User create(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public void update(UserDto userDto) {
        User user = userRepository.findOne(userDto.getUid());
        if (user == null) {
            throw new IllegalArgumentException();
        }

        user.setJoinAt(userDto.getJoinAt());
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setNickname(userDto.getNickname());
        user.setBirthday(userDto.getBirthday());
        user.setPhone(userDto.getPhone());
        userRepository.saveAndFlush(new User(userDto));
    }

    public void update(User user) {
        if (!userRepository.exists(user.getUid()))
            throw new IllegalArgumentException();

        userRepository.saveAndFlush(user);
    }

    public void remove(long uid) {
        if (!userRepository.exists(uid))
            throw new IllegalArgumentException();

        userRepository.delete(uid);
    }

    @Transactional
    public long storeAvatar(long uid, MultipartFile multipartFile) {
        User user = userRepository.findOne(uid);
        if (multipartFile == null || user == null) return 0L;

        Long gid = avatarRepository.count() + 1;
        String extension = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
        String hash = HashUtil.encrypt(uid, "SHA-256");
        long fileSize = 0L;
        try {
            fileSize = FileModule.storeFile(hash, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Avatar avatar = new Avatar();
        avatar.setGid(gid);
        avatar.setName(hash);
        avatar.setExtension(extension);
        avatar.setUpdateAt(ZonedDateTime.now());
        avatar.setCreateAt(ZonedDateTime.now());
        avatar.setSize(fileSize);
        user.setAvatar(avatar);
        userRepository.save(user);
        return fileSize;
    }

    @Transactional
    public long changeAvatar(long uid, MultipartFile multipartFile) {
        User user = userRepository.findOne(uid);
        if (multipartFile == null || user == null || user.getAvatar() == null) throw new IllegalArgumentException();

        Avatar avatar = user.getAvatar();
        String extension = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
        String hash = HashUtil.encrypt(uid, "SHA-256");
        long fileSize = 0L;
        try {
            fileSize = FileModule.coverFile(avatar.getName(), hash + "." + extension, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        avatar.setUpdateAt(ZonedDateTime.now());
        avatar.setExtension(extension);
        avatar.setName(hash);
        avatar.setSize(fileSize);
        user.setAvatar(avatar);
        userRepository.saveAndFlush(user);
        return fileSize;
    }

    @Transactional(rollbackFor = IllegalArgumentException.class)
    public void removeAvatar(long uid) {
        User user = userRepository.findOne(uid);
        if (user == null || user.getAvatar() == null) throw new IllegalArgumentException();

        Avatar avatar = user.getAvatar();
        boolean isComplete = FileModule.removeFile(avatar.getName());
        if (!isComplete) {
            throw new NotFoundException("File Not Found");
        }

        user.setAvatar(null);
        avatarRepository.delete(avatar);
        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String token) throws UsernameNotFoundException {
        return userRepository.findUserByToken(token);
    }

}
