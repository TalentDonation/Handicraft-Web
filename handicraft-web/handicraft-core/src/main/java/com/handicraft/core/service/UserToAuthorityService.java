package com.handicraft.core.service;

import com.handicraft.core.dto.User;
import com.handicraft.core.dto.UserToAuthority;
import com.handicraft.core.repository.UserRepository;
import com.handicraft.core.repository.UserToAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserToAuthorityService implements UserDetailsService {

    @Autowired
    UserToAuthorityRepository userToAuthorityRepository;


    @Override
    public UserToAuthority loadUserByUsername(String uid) {

        return userToAuthorityRepository.findOne(Long.parseLong(uid));
    }

    @Modifying
    public UserToAuthority update(UserToAuthority userToAuthority)
    {
        return userToAuthorityRepository.saveAndFlush(userToAuthority);
    }
}
