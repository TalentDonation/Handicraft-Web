package com.handicraft.core.service;

import com.handicraft.core.dto.User;

import java.util.List;

public interface UserService {

    User findByUser(int u_id);

    List<User> findByUserAll();

    User insertToUser(User user);

    User updateToUser(User user);

    Boolean deleteToUser(int u_id);

    User findLastUser();

}
