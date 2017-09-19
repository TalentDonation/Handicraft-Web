package com.handicraft.core.service;


import com.handicraft.core.dto.UserToImage;
import com.handicraft.core.repository.UserToImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserToImageServiceImp implements UserToImageService{

    @Autowired
    UserToImageRepository userToImageRepository;

    @Override
    public UserToImage insertToUserToImage(UserToImage userToImage) {
        return userToImageRepository.save(userToImage);
    }

    @Override
    public UserToImage findToUserToImage(long uid) {
        return userToImageRepository.findOne(uid);
    }

    @Override
    public UserToImage deleteUserToImage(long uid) {

        UserToImage userToImage = findToUserToImage(uid);

        userToImageRepository.delete(uid);

        return userToImage;
    }
}
