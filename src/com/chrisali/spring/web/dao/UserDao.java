package com.chrisali.spring.web.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("userDao")
@Repository
public class UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Session session;
	
	public Session getSession() {
		session = null;
		
		try {session = sessionFactory.getCurrentSession();} 
		catch (HibernateException e) {session = sessionFactory.openSession();}
		
		return session;
	}
	
	public void closeSession() {session.close();}
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void create(User user) {
		Transaction tx = null;
		session = sessionFactory.openSession();
		try {
			tx = session.beginTransaction();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			session.save(user);
			session.flush();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)	tx.rollback();
		} finally {
			session.close();
		}
	}

	public boolean exists(String username) {
		return getUser(username) != null;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		List<User> users = getSession().createQuery("from User").list();
		
		closeSession();
				
		return users;
	}

	public User getUser(String username) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.idEq(username));
		User user = (User) criteria.uniqueResult();
		
		closeSession();
		
		return user;
	}
}
