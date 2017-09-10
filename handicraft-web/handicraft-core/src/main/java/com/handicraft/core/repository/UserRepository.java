package com.handicraft.core.repository;

import com.handicraft.core.dto.Image;
import com.handicraft.core.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findTopByOrderByUidDesc();
}
