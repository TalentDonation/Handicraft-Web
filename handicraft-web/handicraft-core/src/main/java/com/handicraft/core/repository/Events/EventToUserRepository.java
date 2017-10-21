package com.handicraft.core.repository.Events;

import com.handicraft.core.dto.Events.EventToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventToUserRepository extends JpaRepository<EventToUser , Long >{

    List<EventToUser> findByStartGreaterThanEqualAndEndLessThanEqual(Date start , Date end);

    void deleteAllByUserList(long uid);
}
