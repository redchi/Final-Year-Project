package core.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Service;

import core.tradingsystem.tradingbot.TradingBot;

@Service
public class DataBaseConnect {

	private EntityManagerFactory emf;
	
	public DataBaseConnect() {
		emf = Persistence.createEntityManagerFactory("p1");
	}
	
	public synchronized boolean checkUsernameExists(String username) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		boolean exists = true;
		try {
		User user = em
	            .createQuery("Select u from User u where u.username='"+username+"'", User.class)
	            .getSingleResult();
		}
		catch(Exception e) {
			exists = false;
		}
		em.getTransaction().commit();
		em.close();
		return exists;
	}
	
	public synchronized boolean checkEmailExists(String email) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		boolean exists = true;
		try {
		User user = em
	            .createQuery("Select u from User u where u.email='"+email+"'", User.class)
	            .getSingleResult();
		}
		catch(Exception e) {
			exists = false;
		}
		em.getTransaction().commit();
		em.close();
		return exists;
	}
	
	public synchronized User getUser(String username) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		User user = null;
		try {
		user = em
	            .createQuery("Select u from User u where u.username='"+username+"'", User.class)
	            .getSingleResult();
		}
		catch(Exception e) {
			user =  null;
		}
		
		em.getTransaction().commit();
		em.close();
		return user;
	}
	
	public synchronized User getUserByEmail(String email) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		User user = null;
		try {
		user = em
	            .createQuery("Select u from User u where u.email='"+email+"'", User.class)
	            .getSingleResult();
		}
		catch(Exception e) {
			user =  null;
		}
		
		em.getTransaction().commit();
		em.close();
		return user;
	}
	
	
	public synchronized void updateUserPassword(String username,String hashedPassword) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		User user = null;
		try {
		user = em
	            .createQuery("Select u from User u where u.username='"+username+"'", User.class)
	            .getSingleResult();
		user.setPassword(hashedPassword);
		
		}
		catch(Exception e) {
			
		}
		em.getTransaction().commit();
		em.close();
	}
	
	public synchronized void addNewUser(User user) {
	   EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}

}
