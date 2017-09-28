package com.handicraft.core.service;

import com.handicraft.core.dto.Event;
import com.handicraft.core.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public void insert(Event event)
    {
        eventRepository.save(event);
    }

    public Event update(Event event)
    {
       return eventRepository.save(event);
    }

    public List<Event> find()
    {
        return eventRepository.findAll();
    }

    public void remove(Event event)
    {
        eventRepository.delete(event);
    }
}
