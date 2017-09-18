package com.handicraft.core.service;


import com.handicraft.core.dto.UserToImage;
import com.handicraft.core.repository.UserToImageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserToImageService {

    UserToImage insertToUserToImage(UserToImage userToImage);


}
