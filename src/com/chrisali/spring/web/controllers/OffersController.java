package com.chrisali.spring.web.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chrisali.spring.web.dao.FormValidationGroup;
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
	public String createOffer(Model model, Principal principal) {
		Offer offer = null;
		
		if (principal != null) {
			String username = principal.getName();
			offer = offersService.getOffer(username);
			model.addAttribute("edit", true);
		}
		
		if (offer == null) {
			offer = new Offer();
			model.addAttribute("edit", false);
		}
		
		model.addAttribute("offer", offer);
		
		return "createoffer";
	}
	
	@RequestMapping(value="/docreateoffer", method=RequestMethod.POST)
	public String doCreateOffer(@Validated(FormValidationGroup.class) Offer offer, BindingResult result, Principal principal, @RequestParam(value="delete", required=false) String delete) {
		
		if (result.hasErrors())
			return "createoffer";
		
		if (delete == null) {
			String username = principal.getName();
			offer.getUser().setUsername(username);
			offersService.createOrUpdate(offer);
			return "offercreated";
		} else {
			offersService.delete(offer.getId());
			return "offerdeleted";
		}
	}
}
