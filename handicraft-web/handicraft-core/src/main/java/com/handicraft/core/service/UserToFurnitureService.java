package com.handicraft.core.service;

import com.handicraft.core.dto.UserToFurniture;
import com.handicraft.core.repository.UserToFurnitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class UserToFurnitureService {

    @Autowired
    UserToFurnitureRepository userToFurnitureRepository;

    public UserToFurniture find(long uid)
    {
        return userToFurnitureRepository.findOne(uid);
    }

    @Modifying
    public UserToFurniture update(UserToFurniture userToFurniture)
    {
        return userToFurnitureRepository.save(userToFurniture);
    }
}
