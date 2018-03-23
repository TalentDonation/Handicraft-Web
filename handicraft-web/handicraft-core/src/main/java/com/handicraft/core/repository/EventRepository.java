package com.handicraft.core.repository;

import com.handicraft.core.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByUserUid(long uid);

    List<Event> findByStartBetween(ZonedDateTime start, ZonedDateTime end);

    void deleteByUserUid(long uid);
}
