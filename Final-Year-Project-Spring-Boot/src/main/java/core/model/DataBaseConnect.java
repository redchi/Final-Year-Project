package core.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Service;

import core.tradingsystem.tradingbot.TradingBot;

/**
 * The Class DataBaseConnect.
 */
@Service
public class DataBaseConnect {

	/** The EntityManagerFactory. */
	private EntityManagerFactory emf;
	
	/**
	 * Instantiates a new data base connect.
	 */
	public DataBaseConnect() {
		emf = Persistence.createEntityManagerFactory("p1");
	}
	
	/**
	 * Checks if username is linked to a user
	 *
	 * @param username the username
	 * @return true, if successful
	 */
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
	
	/**
	 * Checks if email is linked to a users.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
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
	
	/**
	 * Gets the user by username
	 *
	 * @param username the username
	 * @return the user
	 */
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
	
	/**
	 * Gets the user by email.
	 *
	 * @param email the email
	 * @return the user by email
	 */
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
	
	
	/**
	 * Updates a users password.
	 *
	 * @param username the username
	 * @param hashedPassword the hashed password
	 */
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
	
	/**
	 * Adds the new user.
	 *
	 * @param user the user
	 */
	public synchronized void addNewUser(User user) {
	   EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}

}
