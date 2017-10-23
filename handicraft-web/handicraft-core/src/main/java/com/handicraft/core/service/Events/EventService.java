package com.handicraft.core.service.Events;

import com.handicraft.core.dto.Events.Event;
import com.handicraft.core.repository.Events.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public Event insert(Event event)
    {
        return eventRepository.save(event);
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

    public void removeById(long eid)
    {
        eventRepository.delete(eid);
    }

    public Event findById(long eid)
    {
        if(!eventRepository.exists(eid))    return null;
        return eventRepository.findOne(eid);
    }
}
