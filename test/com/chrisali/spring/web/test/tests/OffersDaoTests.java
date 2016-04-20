package com.chrisali.spring.web.test.tests;

import static org.junit.Assert.*;

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
	
	// Test Users
	private User user1 = new User("johnwpurcell", "hellothere", "john@caveofprogramming.com", 
			true, "ROLE_USER", "John Purcell");
	private User user2 = new User("richardhannay", "the39steps", "richard@caveofprogramming.com", 
				true, "ROLE_ADMIN", "Richard Hannay");
	private User user3 = new User("suetheviolinist", "iloveviolins", "sue@caveofprogramming.com", 
				true, "ROLE_USER", "Sue Black");
	private User user4 = new User("rogerblake", "liberator", "rog@caveofprogramming.com", 
				false, "user", "Rog Blake");
	
	// Test Offers
	private Offer offer1 = new Offer("This is a test offer.", user1);
	private Offer offer2 = new Offer("This is another test offer.", user1);
	private Offer offer3 = new Offer("This is yet another test offer.", user2);
	private Offer offer4 = new Offer("This is a test offer once again.", user3);
	private Offer offer5 = new Offer("Here is an interesting offer of some kind.", user3);
	private Offer offer6 = new Offer("This is just a test offer.", user3);
	private Offer offer7 = new Offer("This is a test offer for a user that is not enabled.", user4);
	
	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		
		jdbc.execute("delete from offers");
		jdbc.execute("delete from users");
	}
	
	@Test
	public void testCreateRetrieve() {
		userDao.create(user1);
		userDao.create(user2);
		userDao.create(user3);
		userDao.create(user4);
		
		offersDao.create(offer1);
		
		List<Offer> offers1 = offersDao.getOffers();
		assertEquals("Number of offers should be 1", 1, offers1.size());
		
		assertEquals("Retrieved offer should equal inserted offer", offer1, offers1.get(0));
		
		offersDao.createOrUpdate(offer1);
		offersDao.createOrUpdate(offer2);
		offersDao.createOrUpdate(offer3);
		offersDao.createOrUpdate(offer4);
		offersDao.createOrUpdate(offer5);
		offersDao.createOrUpdate(offer6);
		offersDao.createOrUpdate(offer7);
		
		List<Offer> offers2 = offersDao.getOffers();
		assertEquals("Number of offers from enabled users should be 6", 6, offers2.size());
	}
	
	@Test
	public void testUpdate() {
		userDao.create(user1);
		userDao.create(user2);
		userDao.create(user3);
		userDao.create(user4);
		
		offersDao.createOrUpdate(offer1);
		offersDao.createOrUpdate(offer2);
		offersDao.createOrUpdate(offer3);
		offersDao.createOrUpdate(offer4);
		offersDao.createOrUpdate(offer5);
		offersDao.createOrUpdate(offer6);
		offersDao.createOrUpdate(offer7);
		
		offer3.setText("This offer has updated text");
		offersDao.createOrUpdate(offer3);
		
		Offer retrieved = offersDao.getOffer(offer3.getId());
		assertEquals("Retrieved offer should be updated", offer3, retrieved);
	}
	
	@Test
	public void testGetUsername() {
		userDao.create(user1);
		userDao.create(user2);
		userDao.create(user3);
		userDao.create(user4);
		
		offersDao.createOrUpdate(offer1);
		offersDao.createOrUpdate(offer2);
		offersDao.createOrUpdate(offer3);
		offersDao.createOrUpdate(offer4);
		offersDao.createOrUpdate(offer5);
		offersDao.createOrUpdate(offer6);
		offersDao.createOrUpdate(offer7);
		
		List<Offer> offers1 = offersDao.getOffers(user3.getUsername());
		assertEquals("Should be 3 offers for user", 3, offers1.size());
		
		List<Offer> offers2 = offersDao.getOffers("nobody");
		assertEquals("Should be 0 offers for user", 0, offers2.size());
		
		List<Offer> offers3 = offersDao.getOffers(user2.getUsername());
		assertEquals("Should be 1 offer for user", 1, offers3.size());
	}
	
	@Test
	public void testDelete() {
		userDao.create(user1);
		userDao.create(user2);
		userDao.create(user3);
		userDao.create(user4);
		
		offersDao.createOrUpdate(offer1);
		offersDao.createOrUpdate(offer2);
		offersDao.createOrUpdate(offer3);
		offersDao.createOrUpdate(offer4);
		offersDao.createOrUpdate(offer5);
		offersDao.createOrUpdate(offer6);
		offersDao.createOrUpdate(offer7);

		Offer retrieved1 = offersDao.getOffer(offer2.getId());
		assertNotNull("Offer with ID " + retrieved1.getId() + " should not be null", retrieved1);
		
		offersDao.delete(offer2.getId());
		
		Offer retrieved2 = offersDao.getOffer(offer2.getId());
		assertNull("Offer with ID " + retrieved1.getId() + " should be null", retrieved2);
	}
	
	@Test
	public void testGetById() {
		userDao.create(user1);
		userDao.create(user2);
		userDao.create(user3);
		userDao.create(user4);
		
		offersDao.createOrUpdate(offer1);
		offersDao.createOrUpdate(offer2);
		offersDao.createOrUpdate(offer3);
		offersDao.createOrUpdate(offer4);
		offersDao.createOrUpdate(offer5);
		offersDao.createOrUpdate(offer6);
		offersDao.createOrUpdate(offer7);
		
		Offer retrieved1 = offersDao.getOffer(offer7.getId());
		assertNull("Disabled user's offer should return null", retrieved1);
		
		Offer retrieved2 = offersDao.getOffer(offer1.getId());
		assertEquals("Offers should match", offer1, retrieved2);
	}
}
