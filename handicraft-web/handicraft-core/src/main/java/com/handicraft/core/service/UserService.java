package com.handicraft.core.service;

import com.handicraft.core.dto.User;

import java.util.List;

public interface UserService {

    User getByUser(int u_id);

    List<User> getByUserAll();

    User insertToUser(User user);

    User updateToUser(User user);
}
