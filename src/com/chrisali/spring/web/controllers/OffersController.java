package com.chrisali.spring.web.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chrisali.spring.web.dao.Offer;
import com.chrisali.spring.web.service.OffersService;

@Controller
public class OffersController {
	
	private OffersService offersService;
	
	@Autowired
	public void setOffersService(OffersService offersService) {
		this.offersService = offersService;
	}
	
	@RequestMapping("/offers")
	public String showOffers(Model model) {
		List<Offer> offers = offersService.getCurrent();
		model.addAttribute("offers", offers);
		
		return "offers";
	}
	
	@RequestMapping("/createoffer")
	public String createOffer(Model model) {
		model.addAttribute("offer",new Offer());
		
		return "createoffer";
	}
	
	@RequestMapping(value="/docreateoffer", method=RequestMethod.POST)
	public String doCreateOffer(@Valid Offer offer, BindingResult result, Principal principal) {
		
		if(result.hasErrors())
			return "createoffer";
		
		String username = principal.getName();
		offer.getUser().setUsername(username);
		offersService.create(offer);
		
		return "offercreated";
	}
}
