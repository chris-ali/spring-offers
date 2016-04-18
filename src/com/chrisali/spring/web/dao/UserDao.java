package com.chrisali.spring.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDao")
public class UserDao {
	
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// Either autowiring way is fine
	
	@Autowired
	public void setJdbcSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	@Transactional
	public boolean create(User user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", user.getUsername());
		params.addValue("password", passwordEncoder.encode(user.getPassword()));
		params.addValue("email", user.getEmail());
		params.addValue("enabled", user.isEnabled());
		params.addValue("authority", user.getAuthority());
		
		jdbc.update("insert into users (username, password, email, enabled) values (:username, :password, :email, :enabled)", params);
		
		return jdbc.update("insert into authorities (username, authority) values (:username, :authority)", params) == 1;
	}
	
	public User getUser(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		
		return jdbc.queryForObject("select * from users where username = :username", params, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setEnabled(rs.getBoolean("enabled"));
				
				return user;
			}
		});
	}

	public boolean exists(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		
		return jdbc.queryForObject("select count(*) from users where username = :username", params, Integer.class) >= 1;
	}

	public List<User> getAllUsers() {
			return jdbc.query("select * from users, authorities where users.username=authorities.username", BeanPropertyRowMapper.newInstance(User.class));
	}
	
}
