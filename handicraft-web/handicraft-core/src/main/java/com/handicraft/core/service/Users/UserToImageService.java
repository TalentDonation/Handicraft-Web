package com.handicraft.core.service.Users;


import com.handicraft.core.dto.Users.UserToImage;
import com.handicraft.core.repository.Users.UserToImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserToImageService {

    @Autowired
    UserToImageRepository userToImageRepository;

    public UserToImage insertToUserToImage(UserToImage userToImage) {
        return userToImageRepository.saveAndFlush(userToImage);
    }

    public UserToImage findToUserToImage(long uid) {
        return userToImageRepository.findOne(uid);
    }

    public UserToImage deleteUserToImage(long uid) {

        UserToImage userToImage = findToUserToImage(uid);

        userToImageRepository.delete(uid);

        return userToImage;
    }
}
