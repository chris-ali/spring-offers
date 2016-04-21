package com.chrisali.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.chrisali.spring.web.dao.Message;
import com.chrisali.spring.web.dao.MessagesDao;
import com.chrisali.spring.web.dao.User;
import com.chrisali.spring.web.dao.UserDao;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessagesDao messagesDao;

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
	
	public void sendMessage(Message message) {
		System.out.println(message);
		messagesDao.createOrUpdate(message);
	}
}
