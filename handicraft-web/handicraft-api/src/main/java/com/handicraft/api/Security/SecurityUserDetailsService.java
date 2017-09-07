package com.handicraft.api.Security;

import com.handicraft.api.exception.NotFoundException;
import com.handicraft.api.exception.UnAuthorizedException;
import com.handicraft.core.dto.User;
import com.handicraft.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;


    @Override
    public SecurityUserDetails loadUserByUsername(String fid) throws UsernameNotFoundException {

        User user = userRepository.findOne(Integer.parseInt(fid));

        if(user == null)    throw new UnAuthorizedException();

        return new SecurityUserDetails(user , AuthorityUtils.createAuthorityList());
    }
}
