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

	@Autowired
	private SessionFactory sessionFactory;
			
	@Override
	public List<Comment> getComments() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Comment> theQuery = currentSession.createQuery("from Comment order by lastUpdate", Comment.class);
		List<Comment> comments = theQuery.getResultList();
		return comments;
	}

	@Override
	public void saveComment(Comment theComment) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theComment);
	}

	@Override
	public Comment getComment(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Comment theComment = currentSession.get(Comment.class, theId);
		return theComment;
	}

	@Override
	public void deleteComment(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<?> theQuery = currentSession.createQuery("delete from Comment where id=:commentId");
		theQuery.setParameter("commentId", theId);
		theQuery.executeUpdate();
	}
}
