package com.handicraft.core.repository;

import com.handicraft.core.dto.EventToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventToUserRepository extends JpaRepository<EventToUser , Long >{
}
