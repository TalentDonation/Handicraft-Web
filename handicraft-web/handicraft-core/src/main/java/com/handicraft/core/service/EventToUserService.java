package com.handicraft.core.service;

import com.handicraft.core.dto.Event;
import com.handicraft.core.dto.EventToUser;
import com.handicraft.core.repository.EventToUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventToUserService {

    @Autowired
    EventToUserRepository eventToUserRepository;

    public EventToUser insert(EventToUser eventToUser)
    {
        return eventToUserRepository.save(eventToUser);
    }

    public EventToUser update(EventToUser eventToUser)
    {
        return eventToUserRepository.save(eventToUser);
    }

    public List<EventToUser> find()
    {
        return eventToUserRepository.findAll();
    }

    public void remove(long eid)
    {
        eventToUserRepository.delete(eid);
    }
}
