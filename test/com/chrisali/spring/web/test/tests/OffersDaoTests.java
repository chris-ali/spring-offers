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
	private DataSource dataSource;
	
	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		
		jdbc.execute("delete from offers");
		jdbc.execute("delete from users");
		jdbc.execute("delete from authorities");
	}
	
	@Test
	public void testCreateUser() {
		Offer offer = new Offer("Chris", "chris@test.com", "A test text string for JUnit test.");
		assertTrue("Offer creation should return true", offersDao.create(offer));
		
		List<Offer> offers = offersDao.getOffers();
		
		assertEquals("Only one offer in list", 1, offers.size());
		
		assertEquals("Created offer should be identical to retreived offer", offer, offers.get(0));
		
		offer = offers.get(0);
		offer.setText("An updated test text string for JUnit test.");
		
		assertTrue("Offer update should return true", offersDao.update(offer));
		
		Offer updated = offersDao.getOffer(offer.getId());
		
		assertEquals("Updated offer should match retreived offer", offer, updated);
		
		offersDao.delete(offer.getId());
		
		List<Offer> empty = offersDao.getOffers();
		
		assertEquals("Offers list should be empty", 0, empty.size());
	}
}
