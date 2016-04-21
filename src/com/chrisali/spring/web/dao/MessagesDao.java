package com.chrisali.spring.web.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
@Component("messagesDao")
public class MessagesDao {
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
	
	@SuppressWarnings("unchecked")
	public List<Message> getMessages() {
		Criteria criteria = getSession().createCriteria(Message.class);
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getMessages(String username) {
		Criteria criteria = getSession().createCriteria(Message.class);
		criteria.add(Restrictions.eq("username", username));
		
		List<Message> messages = criteria.list();
		closeSession();
		
		return messages;
	}
	
	public Message getMessage(int id) {	
		Criteria criteria = getSession().createCriteria(Message.class);
		criteria.add(Restrictions.idEq(id));
		
		Message message = (Message)criteria.uniqueResult();
		closeSession();
		
		return message;
	}
	
	public void createOrUpdate(Message message) {
		Transaction tx = null;
		session = sessionFactory.openSession();
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(message);
			session.flush();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)	tx.rollback();
		} finally {
			session.close();
		}
	}
	
	public boolean delete(int id) {
		Query query = getSession().createQuery("delete from Message where id=:id");
		query.setLong("id", id);
		
		boolean isDeleted = (query.executeUpdate() == 1);
		closeSession();
		
		return isDeleted;
	}
}
