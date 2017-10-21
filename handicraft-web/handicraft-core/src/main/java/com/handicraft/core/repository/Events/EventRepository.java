package com.handicraft.core.repository.Events;

import com.handicraft.core.dto.Events.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event , Long> {
}
