package com.handicraft.core.repository;

import com.handicraft.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByToken(String token);

    User findByAvatarName(String name);
}
