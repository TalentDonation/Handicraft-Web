package com.handicraft.core.repository.Users;

import com.handicraft.core.dto.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findTopByOrderByUidDesc();
}
