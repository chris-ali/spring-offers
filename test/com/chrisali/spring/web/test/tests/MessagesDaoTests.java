package com.chrisali.spring.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import com.chrisali.spring.web.dao.Message;
import com.chrisali.spring.web.dao.MessagesDao;
import com.chrisali.spring.web.dao.Offer;
import com.chrisali.spring.web.dao.OffersDao;
import com.chrisali.spring.web.dao.User;
import com.chrisali.spring.web.dao.UserDao;

@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/chrisali/spring/web/container/dao-context.xml",
									"classpath:com/chrisali/spring/web/container/security-context.xml",
									"classpath:com/chrisali/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MessagesDaoTests {
	
	@Autowired
	private OffersDao offersDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessagesDao messagesDao;
	
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
	
	// Test Messages
	private Message message1 = new Message("Test Subject1", "Test Content1", "Chris Ali", "chris@test.com", user1.getUsername());
	private Message message2 = new Message("Test Subject2", "Test Content2", "Matt Ali", "matt@test.com", user2.getUsername());
	private Message message3 = new Message("Test Subject3", "Test Content3", "Chris Ali", "chris@test.com", user3.getUsername());
	
	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		
		jdbc.execute("delete from offers");
		jdbc.execute("delete from messages");
		jdbc.execute("delete from users");
		
	}
	
	private void setTestData() {
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
		
		messagesDao.createOrUpdate(message1);
		messagesDao.createOrUpdate(message2);
		messagesDao.createOrUpdate(message3);
	}
	
	@Test
	public void testCreateRetrieve() {
		userDao.create(user1);
		
		messagesDao.createOrUpdate(message1);
		
		List<Message> messages = messagesDao.getMessages();
		
		assertEquals("Message in database should be equal", message1, messages.get(0));
		assertEquals("Should be one message in database", 1, messages.size());
	}
	
	@Test
	public void testRetrieveById() {
		setTestData();
		
		List<Message> messages = messagesDao.getMessages();
		
		for (Message message : messages) {
			Message retrieved = messagesDao.getMessage(message.getId());
			assertEquals("Retrieved message should be equal", message, retrieved);
		}
		
		Message toDelete = messages.get(1);
		
		assertNotNull("Message not deleted yet", messagesDao.getMessage(toDelete.getId()));
		
		messagesDao.delete(toDelete.getId());
		
		assertNull("Message was deleted", messagesDao.getMessage(toDelete.getId()));
	}
}
