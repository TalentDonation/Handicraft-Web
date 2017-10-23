package com.handicraft.core.repository.Users;

import com.handicraft.core.dto.Users.UserToEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToEventRepository extends JpaRepository<UserToEvent , Long>{

}
