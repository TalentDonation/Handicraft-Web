package com.handicraft.core.service;

import com.handicraft.core.domain.Event;
import com.handicraft.core.domain.User;
import com.handicraft.core.dto.EventDto;
import com.handicraft.core.repository.EventRepository;
import com.handicraft.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private EventRepository eventRepository;
    private UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<EventDto> findAll() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(EventDto::new).collect(Collectors.toList());
    }

    public List<EventDto> findBetween(String start, String end) {
        List<Event> events = eventRepository.findByStartBetween(ZonedDateTime.parse(start), ZonedDateTime.parse(end));
        return events.stream().map(EventDto::new).collect(Collectors.toList());
    }

    public List<EventDto> find(long uid) {
        List<Event> events = eventRepository.findByUserUid(uid);

        return events.stream()
                .map(EventDto::new)
                .collect(Collectors.toList());
    }

    public EventDto findOne(long eid) {
        Event event = eventRepository.findOne(eid);
        return new EventDto(event);
    }


    public Event create(EventDto eventDto) {
        User user = userRepository.findOne(eventDto.getUid());
        if (user == null) {
            throw new IllegalArgumentException();
        }

        Event event = new Event(eventDto);
        event.setUser(user);
        return eventRepository.save(event);
    }

    public void update(long uid, List<EventDto> eventDtos) {
        List<Event> events = new ArrayList<>();
        eventDtos.forEach(object -> {
            if (!eventRepository.exists(object.getEid())) {
                throw new IllegalArgumentException();
            }

            Event event = new Event(object);
            events.add(event);
        });

        eventRepository.save(events);
    }

    public void updateOne(EventDto eventDto) {
        if (!eventRepository.exists(eventDto.getEid())) {
            throw new IllegalArgumentException();
        }

        Event event = new Event(eventDto);
        eventRepository.save(event);
    }

    public void removeByUid(long uid) {
        eventRepository.deleteByUserUid(uid);
    }

    public void removeByEid(long eid) {
        eventRepository.delete(eid);
    }
}
