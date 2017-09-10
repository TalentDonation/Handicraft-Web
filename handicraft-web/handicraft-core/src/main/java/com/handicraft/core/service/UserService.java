package com.handicraft.core.service;

import com.handicraft.core.dto.User;

import java.util.List;

public interface UserService {

    User findByUser(long u_id);

    List<User> findByUserAll();

    User insertToUser(User user);

    User updateToUser(User user);

    Boolean deleteToUser(long u_id);

    User findLastUser();

}
