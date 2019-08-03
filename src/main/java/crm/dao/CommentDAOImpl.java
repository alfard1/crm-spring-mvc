package crm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crm.entity.Comment;

@Repository
public class CommentDAOImpl implements CommentDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
			
	@Override
	public List<Comment> getComments() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// create a query  ... sort by last name
		Query<Comment> theQuery = 
				currentSession.createQuery("from Comment order by lastUpdate",
											Comment.class);
		
		// execute query and get result list
		List<Comment> comments = theQuery.getResultList();
				
		// return the results		
		return comments;
	}

	@Override
	public void saveComment(Comment theComment) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/upate the customer, finally
		currentSession.saveOrUpdate(theComment);
		
	}

	@Override
	public Comment getComment(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		Comment theComment = currentSession.get(Comment.class, theId);
		
		return theComment;
	}

	@Override
	public void deleteComment(int theId) {

		//System.out.println("### DAO Impl 1 Opening session");
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//System.out.println("### DAO Impl 2");
		
		// delete object with primary key
		Query<?> theQuery = 
				currentSession.createQuery("delete from Comment where id=:commentId");
		theQuery.setParameter("commentId", theId);
		
		//System.out.println("### DAO Impl 3");
		
		theQuery.executeUpdate();	
		
		//System.out.println("### DAO Impl 4");
	}

}











