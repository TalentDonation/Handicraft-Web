package com.handicraft.core.service;

import com.handicraft.core.dao.UserDao;
import com.handicraft.core.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImp implements UserService{

	@Autowired
	UserDao userDao;

	@Override
	public User getByUser(int u_id) {
		return userDao.findOne(u_id);
	}

	@Override
	public List<User> getByUserAll() {
		return userDao.findAll();
	}

	@Override
	public User insertToUser(User user) {

		if(userDao.exists(user.getUid()))
		{
			user.setUid(userDao.findTopByOrderByUidDesc().getUid() + 1);
		}

		return userDao.save(user);
	}

	@Override
	public User updateToUser(User user) {

		if(userDao.exists(user.getUid()))	return null;

		return userDao.save(user);
	}
}
