package com.handicraft.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handicraft.api.dto.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>{

}
