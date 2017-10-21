package com.handicraft.core.repository.Users;

import com.handicraft.core.dto.Users.UserToAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToAuthorityRepository extends JpaRepository<UserToAuthority , Long>{
}
