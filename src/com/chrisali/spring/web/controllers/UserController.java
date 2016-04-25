package com.chrisali.spring.web.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chrisali.spring.web.dao.FormValidationGroup;
import com.chrisali.spring.web.dao.Message;
import com.chrisali.spring.web.dao.User;
import com.chrisali.spring.web.service.UserService;

@Controller
public class UserController {
	
	private UserService userService;
	
	@Autowired
	private MailSender mailSender;
	
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
	public String doCreateAccount(@Validated(FormValidationGroup.class) User user, BindingResult result, Model model) {
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
	
	@RequestMapping("/messages")
	public String showMessages() {
		return "messages";
	}
	
	@RequestMapping(value="/getmessages", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> getMessages(Principal principal) {
		
		List<Message> messages = null;
		if (principal == null) {
			messages = new ArrayList<>();
		} else {
			String username = principal.getName();
			messages = userService.getMessages(username);
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("messages", messages);
		data.put("number", messages.size());
		
		return data;
	}
	
	@RequestMapping(value="/sendmessage", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendMessage(Principal principal, @RequestBody Map<String, Object> data) {
		String name = (String)data.get("name");
		String email = (String)data.get("email");
		String text = (String)data.get("text");
		Integer target = (Integer)data.get("target");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("offers.donotreply@gmail.com");
		mail.setTo(email);
		mail.setSubject("Offers: You have a new message from " + name);
		mail.setText(text);
		
		Map<String, Object> returnVal = new HashMap<>();
		returnVal.put("success", true);
		returnVal.put("target", target);
		
		try {mailSender.send(mail);}
		catch (Exception e) {
			e.printStackTrace();
			returnVal.put("success", false);
		}
		
		return returnVal;
	}
}
