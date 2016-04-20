package com.chrisali.spring.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chrisali.spring.web.dao.Offer;
import com.chrisali.spring.web.dao.OffersDao;
import com.chrisali.spring.web.dao.User;
import com.chrisali.spring.web.dao.UserDao;

@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/chrisali/spring/web/container/dao-context.xml",
									"classpath:com/chrisali/spring/web/container/security-context.xml",
									"classpath:com/chrisali/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OffersDaoTests {
	
	@Autowired
	private OffersDao offersDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		
		jdbc.execute("delete from offers");
		jdbc.execute("delete from users");
	}
	
	@Test
	public void testOffers() {
		// Create user for offer
		User user = new User("chris", "test1", "chris@test.com", true, "ROOT_USER", "Chris Ali");
		userDao.create(user);
		
		List<User> users = userDao.getAllUsers();
		
		assertEquals("Only one user in list", 1, users.size());
		assertTrue("User should exist in database", userDao.exists(user.getUsername()));
		
		// Create single offer
		Offer offer = new Offer("A test text string for a JUnit test.", user);
		assertTrue("Offer creation should return true", offersDao.create(offer));
		
		List<Offer> singleOffer = offersDao.getOffers();
		
		assertEquals("Only one offer in list", 1, singleOffer.size());
		
		assertEquals("Created offer should be identical to retreived offer", offer, singleOffer.get(0));
		
		// Retrieve offer by ID
		offer = singleOffer.get(0);
		offer.setText("An updated test text string for a JUnit test.");
		
		assertTrue("Offer update should return true", offersDao.update(offer));
		
		// Update offer
		Offer updated = offersDao.getOffer(offer.getId());
		assertEquals("Updated offer should match retreived offer", offer, updated);
		
		// Create second offer
		Offer offer2 = new Offer("Second test offer for a JUnit test.", user);
		assertTrue("Offer creation should return true", offersDao.create(offer2));
		
		// Retrieve offers by username
		List<Offer> multipleOffers = offersDao.getOffers();
		assertEquals("The number of offers for this username should be 2", 2, multipleOffers.size());
		
		// Test multiple offer IDs
		for (Offer currentOffer : multipleOffers) {
			Offer retrievedOffer = offersDao.getOffer(currentOffer.getId());
			
			assertEquals("Offer retreived by ID should match offer in list", currentOffer, retrievedOffer);
		}
		
		// Delete offer
		offersDao.delete(offer.getId());
		List<Offer> oneDeleted = offersDao.getOffers();
		assertEquals("Offers list should have one offer left", 1, oneDeleted.size());
	}
}
