package crm.dao;

import crm.entity.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO {

    private final EntityManager entityManager;

    public CommentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Comment> getComments() {
        Query query = entityManager.createQuery("from Comment order by lastUpdate");
        return (List<Comment>) query.getResultList();
    }

    @Override
    public Comment getComment(int id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public void newComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        entityManager.merge(comment);
    }

    @Override
    public void deleteComment(int id) {
        Query query = entityManager.createQuery("delete from Comment where id=:commentId");
        query.setParameter("commentId", id);
        query.executeUpdate();
    }
}
