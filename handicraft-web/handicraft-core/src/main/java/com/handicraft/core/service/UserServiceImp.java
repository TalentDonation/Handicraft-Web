package com.handicraft.core.service;

import com.handicraft.core.repository.UserRepository;
import com.handicraft.core.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;


@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userDao;



	@Override
	@Transactional
	public User findByUser(long u_id) {

		return userDao.findOne(u_id);
	}

	@Override
	public List<User> findByUserAll() {
		return userDao.findAll();
	}

	@Override
	public User insertToUser(User user) {


		return userDao.save(user);
	}

	@Override
	public User updateToUser(User user) {

		if(!userDao.exists(user.getUid()))	return null;

		return userDao.save(user);
	}

	@Override
	public Boolean deleteToUser(long u_id) {

		if(!userDao.exists(u_id))	return false;

		userDao.delete(u_id);

		return true;
	}


	@Override
	public User findLastUser() {
		return userDao.findTopByOrderByUidDesc();
	}
}
