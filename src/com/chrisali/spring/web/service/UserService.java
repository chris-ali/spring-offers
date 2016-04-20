package com.chrisali.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.chrisali.spring.web.dao.User;
import com.chrisali.spring.web.dao.UserDao;

@Service("userService")
public class UserService {

	private UserDao userDao;
	
	@Autowired
	public void setOffersDAO(UserDao userDao) {
		this.userDao = userDao;
	}

	public void create(User user) {
		userDao.create(user);
	}

	public boolean exists(String username) {
		return userDao.exists(username);
	}
	
	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
}
