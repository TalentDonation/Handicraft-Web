package com.handicraft.core.service;


import com.handicraft.core.dto.Authority;
import com.handicraft.core.repository.AuthorityRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;



    public Authority delete(long aid)
    {
        Authority authority = authorityRepository.findOne(aid);

        authorityRepository.delete(aid);

        return authority;
    }
}
