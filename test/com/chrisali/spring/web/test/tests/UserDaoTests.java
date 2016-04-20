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

import com.chrisali.spring.web.dao.User;
import com.chrisali.spring.web.dao.UserDao;

@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/chrisali/spring/web/container/dao-context.xml",
									"classpath:com/chrisali/spring/web/container/security-context.xml",
									"classpath:com/chrisali/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DataSource dataSource;
	
	private User user1 = new User("johnwpurcell", "hellothere", "john@caveofprogramming.com", 
									true, "ROLE_USER", "John Purcell");
	private User user2 = new User("richardhannay", "the39steps", "richard@caveofprogramming.com", 
									true, "ROLE_ADMIN", "Richard Hannay");
	private User user3 = new User("suetheviolinist", "iloveviolins", "sue@caveofprogramming.com", 
									true, "ROLE_USER", "Sue Black");
	private User user4 = new User("rogerblake", "liberator", "rog@caveofprogramming.com", 
									false, "user", "Rog Blake");
	
	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		
		jdbc.execute("delete from offers");
		jdbc.execute("delete from users");
	}
	
	@Test
	public void testCreateRetrieve() {
		userDao.create(user1);
		
		List<User> users1 = userDao.getAllUsers();
		
		assertEquals("One user should be created and retrieved", 1, users1.size());
		assertEquals("Inserted user should match retrieved", user1, users1.get(0));
		
		userDao.create(user2);
		userDao.create(user3);
		userDao.create(user4);
		
		List<User> users2 = userDao.getAllUsers();
		
		assertEquals("One user should be created and retrieved", 4, users2.size());
	}
	
	// TODO reimplement
	@Test
	public void testUsers() {
		User user = new User("chris", "test1", "chris@test.com", true, "ROOT_USER", "Chris Ali");
		userDao.create(user);
		
		List<User> users = userDao.getAllUsers();
		
		assertEquals("Only one user in list", 1, users.size());
		assertTrue("User should exist in database", userDao.exists(user.getUsername()));
		
		assertEquals("Created user should be identical to retreived user", user, users.get(0));
		
		//List<User> empty = userDao.getAllUsers();
	}
}
