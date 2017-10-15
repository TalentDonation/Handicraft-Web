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
public class UserService {

	@Autowired
	UserRepository userDao;

	@Transactional
	public User findByUser(long u_id) {

		return userDao.findOne(u_id);
	}

	public List<User> findByUserAll() {
		return userDao.findAll();
	}

	public User insertToUser(User user) {


		return userDao.save(user);
	}

	public User updateToUser(User user) {

		if(!userDao.exists(user.getUid()))	return null;

		return userDao.save(user);
	}


	public Boolean deleteToUser(long u_id) {

		if(!userDao.exists(u_id))	return false;

		userDao.delete(u_id);

		return true;
	}


	public User findLastUser() {
		return userDao.findTopByOrderByUidDesc();
	}
}
