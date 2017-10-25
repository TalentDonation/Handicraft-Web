package com.handicraft.core.service.Users;

import com.handicraft.core.dto.Users.UserToAuthority;
import com.handicraft.core.repository.Users.UserToAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserToAuthorityService implements UserDetailsService {

    @Autowired
    UserToAuthorityRepository userToAuthorityRepository;


    @Override
    public UserToAuthority loadUserByUsername(String uid) {

        return userToAuthorityRepository.findOne(Long.parseLong(uid));
    }

    public UserToAuthority find(long uid) {

        if(!userToAuthorityRepository.exists(uid)) return null;

        return userToAuthorityRepository.findOne(uid);
    }

    @Modifying
    public UserToAuthority update(UserToAuthority userToAuthority)
    {

        return userToAuthorityRepository.saveAndFlush(userToAuthority);
    }

    public UserToAuthority insert(UserToAuthority userToAuthority)
    {
        return userToAuthorityRepository.save(userToAuthority);
    }
}
