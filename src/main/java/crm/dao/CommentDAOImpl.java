package crm.dao;

import crm.entity.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO {

    private final SessionFactory sessionFactory;

    public CommentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Comment> getComments() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Comment> query = currentSession.createQuery("from Comment order by lastUpdate", Comment.class);
        return query.getResultList();
    }

    @Override
    public void saveComment(Comment comment) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(comment);
    }

    @Override
    public Comment getComment(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Comment.class, id);
    }

    @Override
    public void deleteComment(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<?> query = currentSession.createQuery("delete from Comment where id=:commentId");
        query.setParameter("commentId", id);
        query.executeUpdate();
    }
}
