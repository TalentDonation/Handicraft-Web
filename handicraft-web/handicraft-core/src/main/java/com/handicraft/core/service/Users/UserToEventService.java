package com.handicraft.core.service.Users;

import com.handicraft.core.dto.Users.UserToEvent;
import com.handicraft.core.repository.Users.UserToEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class UserToEventService {

    @Autowired
    UserToEventRepository userToEventRepository;

    public UserToEvent find(long uid)
    {
        return userToEventRepository.findOne(uid);
    }

    public UserToEvent insert(UserToEvent userToEvent)
    {
        return userToEventRepository.save(userToEvent);
    }

    @Modifying
    public UserToEvent update(UserToEvent userToEvent)
    {
        return userToEventRepository.save(userToEvent);
    }

    public UserToEvent delete(long uid)
    {
        UserToEvent deletedResult = userToEventRepository.findOne(uid);

        userToEventRepository.delete(uid);

        return deletedResult;
    }
}
