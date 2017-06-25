package com.handicraft.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handicraft.core.dto.User;



@Repository
public interface UserDao extends JpaRepository<User, Integer>{

}
