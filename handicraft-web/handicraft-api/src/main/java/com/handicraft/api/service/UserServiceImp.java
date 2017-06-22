package com.handicraft.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handicraft.api.dao.UserDao;
import com.handicraft.api.dto.User;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	UserDao userDao;
	
	public User getUser()
	{
		return userDao.getOne(1);
	}
}
