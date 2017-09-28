package com.handicraft.core.service;


import com.handicraft.core.dto.UserToImage;
import com.handicraft.core.repository.UserToImageRepository;
import org.hibernate.annotations.SQLInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.persistence.PostRemove;

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
