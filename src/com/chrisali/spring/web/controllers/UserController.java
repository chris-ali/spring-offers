package com.chrisali.spring.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chrisali.spring.web.dao.User;
import com.chrisali.spring.web.service.UserService;

@Controller
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping("/loggedout")
	public String showLoggedOut() {
		return "loggedout";
	}
	
	@RequestMapping("/createaccount")
	public String showCreateAccount(Model model) {
		model.addAttribute("user", new User());
		
		return "createaccount";
	}
	
	@RequestMapping(value="/docreateaccount", method=RequestMethod.POST)
	public String doCreateAccount(@Valid User user, BindingResult result, Model model) {
		if(result.hasErrors())
			return "createaccount";
		
		user.setEnabled(true);
		user.setAuthority("ROLE_USER");
		
		if (userService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "createaccount";
		}
		
		try {
			userService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "createaccount";
		}
		
		model.addAttribute("user", user);
		
		return "accountcreated";
	}
			
	@RequestMapping("/admin")
	public String showAdmin(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		
		return "admin";
	}
	
	@RequestMapping("/accessdenied")
	public String showAccessDenied() {
		return "accessdenied";
	}
}
