package core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import core.model.User;

public class t1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		t1 t1 = new t1();
		t1.t1();
	}
	
	public void t1() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("p2");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String username ="test1";
		boolean exists = true;
		try {
		User user = em
	            .createQuery("Select u from User u where u.username='"+username+"'", User.class)
	            .getSingleResult();
		System.out.println(user.getEmail());
		}

		catch(Exception e) {
			exists = false;
			System.out.println("FAILED");
		}
		
		em.getTransaction().commit();
		em.close();
		
	}

}
