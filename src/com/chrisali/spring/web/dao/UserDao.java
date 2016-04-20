package com.chrisali.spring.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("userDao")
public class UserDao {
	
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// Either autowiring way is fine
	
	@Autowired
	public void setJdbcSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	@Transactional
	public void create(User user) {
		getSession().save(user);
	}
	
	public User getUser(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		
		return jdbc.queryForObject("select * from users where username = :username", params, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setEnabled(rs.getBoolean("enabled"));
				user.setAuthority(rs.getString("authority"));
				user.setName(rs.getString("name"));
				
				return user;
			}
		});
	}
	
	public boolean delete(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		
		return jdbc.update("delete from users, offers where username=:username", params) == 1;
	}

	public boolean exists(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		
		return jdbc.queryForObject("select count(*) from users where username = :username", params, Integer.class) >= 1;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return getSession().createQuery("from User").list();
		//return jdbc.query("select * from users", BeanPropertyRowMapper.newInstance(User.class));
	}
	
	public Session getSession() {
		Session session = null;
		
		try {session = sessionFactory.getCurrentSession();} 
		catch (HibernateException e) {session = sessionFactory.openSession();}
		
		return session;
	}
	
}
