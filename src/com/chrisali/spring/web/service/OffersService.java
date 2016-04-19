package com.chrisali.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.chrisali.spring.web.dao.Offer;
import com.chrisali.spring.web.dao.OffersDao;

@Service("offersService")
public class OffersService {

	private OffersDao offersDao;
	
	@Autowired
	public void setOffersDAO(OffersDao offersDAO) {
		this.offersDao = offersDAO;
	}
	
	public List<Offer> getCurrent() {
		return offersDao.getOffers();
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public void create(Offer offer) {
		offersDao.create(offer);
	}

	public boolean hasOffer(String name) {
		if(name == null) return false;
		
		List<Offer> offers = offersDao.getOffers(name);
		
		return (offers.size() == 0 ? false : true);
	}

	public Offer getOffer(String username) {
		if (username == null)
			return null;
		
		List<Offer> offers = offersDao.getOffers(username);
		
		if (offers.size() == 0)
			return null;
		
		return offers.get(0);
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public void createOrUpdate(Offer offer) {
		if (offer.getId() != 0)
			offersDao.update(offer);
		else
			offersDao.create(offer);
	}

	public void delete(int id) {
		offersDao.delete(id);
	}
}
