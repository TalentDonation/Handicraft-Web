package com.handicraft.core.service.UserEvent;

import com.handicraft.core.dto.Events.EventToUser;
import com.handicraft.core.dto.UserEvent.UserEvent;
import com.handicraft.core.id.UserEventId;
import com.handicraft.core.repository.Events.EventToUserRepository;
import com.handicraft.core.repository.UserEvent.UserEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserEventService {

    @Autowired
    UserEventRepository userEventRepository;

    @Autowired
    EventToUserRepository eventToUserRepository;

    @Transactional
    public void deleteByUid(long uid)
    {
        EventToUser eventToUser;

        List<UserEvent> userEventList = userEventRepository.findAllByUserEventId_Uid(uid);

        userEventRepository.deleteAllByUserEventId_Uid(uid);


        for(UserEvent userEvent : userEventList)
        {
            eventToUser = eventToUserRepository.findOne(userEvent.getUserEventId().getEid());

            if(eventToUser.getUserList().size() == 1)
            {
                eventToUserRepository.delete(eventToUser.getEid());
            }
        }

    }

    @Transactional
    public void deleteByEid(long eid)
    {
        EventToUser eventToUser;

        List<UserEvent> userEventList = userEventRepository.findAllByUserEventId_Eid(eid);

        userEventRepository.deleteAllByUserEventId_Eid(eid);


        for(UserEvent userEvent : userEventList)
        {
            eventToUser = eventToUserRepository.findOne(userEvent.getUserEventId().getEid());

            if(eventToUser.getUserList().size() == 1)
            {
                eventToUserRepository.delete(eventToUser.getEid());
            }
        }

    }

    @Transactional(rollbackFor = EmptyResultDataAccessException.class)
    public void deleteByUserEventId(UserEventId userEventId)
    {
        userEventRepository.delete(userEventId);

        EventToUser eventToUser = eventToUserRepository.findOne(userEventId.getEid());

        if(eventToUser.getUserList().size() == 1)
        {
            eventToUserRepository.delete(eventToUser.getEid());
        }
    }
}
