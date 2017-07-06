package com.handicraft.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handicraft.core.dao.UserDao;
import com.handicraft.core.dto.User;

import java.util.List;


@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	UserDao userDao;


	public User getUser()
	{

		return userDao.getOne(1);

	}
}
