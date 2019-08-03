package crm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crm.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	// it works only if in DB there is 1 object, for more code should be improved
	@Override
	public User findByUserName(String theUserName) {
		//System.out.println("##### >>>> findByUserName START ");
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		//System.out.println("##### >>>> theUserName =" + theUserName);
		// now retrieve/read from database using username		
		Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
		theQuery.setParameter("uName", theUserName);
		
		//Query<User> theQuery = currentSession.createQuery("from User where userName='admin'", User.class);
		
		User theUser = null;
		
		//System.out.println("##### >>>> theQuery = " + theQuery);
		//System.out.println("##### >>>> try/catch section");
		// if 1 user exists in DB code below works without exception and return user found in DB
		// when exception appears (no user in DB) this run code from 'catch'
		try {		  
			theUser = theQuery.getSingleResult();
			//System.out.println("##### >>>> theUser =" + theUser);
		} catch (Exception e) {
			//System.out.println("##### >>>> Exception e = " + e);
			theUser = null;
		}
		
		//System.out.println("##### >>>> findByUserName DONE ");
		return theUser;
	}

	@Override
	public void save(User theUser) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theUser);
	}

}
