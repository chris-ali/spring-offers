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
@Component("offersDao")
public class OffersDao {
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
	public List<Offer> getOffers() {
		Criteria criteria = getSession().createCriteria(Offer.class);
		criteria.createAlias("user", "u").add(Restrictions.eq("u.enabled", true));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Offer> getOffers(String username) {
		Criteria criteria = getSession().createCriteria(Offer.class);
		criteria.createAlias("user", "u");
		criteria.add(Restrictions.eq("u.enabled", true));
		criteria.add(Restrictions.eq("u.username", username));
		
		List<Offer> offers = criteria.list();
		closeSession();
		
		return offers;
	}
	
	public Offer getOffer(int id) {	
		Criteria criteria = getSession().createCriteria(Offer.class);
		criteria.createAlias("user", "u");
		criteria.add(Restrictions.eq("u.enabled", true));
		criteria.add(Restrictions.idEq(id));
		
		Offer offer = (Offer)criteria.uniqueResult();
		closeSession();
		
		return offer;
	}
	
	public void createOrUpdate(Offer offer) {
		Transaction tx = null;
		session = sessionFactory.openSession();
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(offer);
			session.flush();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)	tx.rollback();
		} finally {
			session.close();
		}
	}
	
	public boolean delete(int id) {
		Query query = getSession().createQuery("delete from Offer where id=:id");
		query.setLong("id", id);
		
		boolean isDeleted = (query.executeUpdate() == 1);
		closeSession();
		
		return isDeleted;
	}
	
	public void create(Offer offer) {
		Transaction tx = null;
		session = sessionFactory.openSession();
		try {
			tx = session.beginTransaction();
			session.save(offer);
			session.flush();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)	tx.rollback();
		} finally {
			session.close();
		}
	}
}
