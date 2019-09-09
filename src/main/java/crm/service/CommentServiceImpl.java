package crm.service;

import crm.dao.CommentDAO;
import crm.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;

    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Override
    @Transactional
    public List<Comment> getComments() {
        return commentDAO.getComments();
    }

    @Override
    @Transactional
    public void newComment(Comment comment) {
        commentDAO.newComment(comment);
    }

    @Override
    @Transactional
    public void updateComment(Comment comment) {
        commentDAO.updateComment(comment);
    }


    @Override
    @Transactional
    public Comment getComment(int id) {
        return commentDAO.getComment(id);
    }

    @Override
    @Transactional
    public void deleteComment(int id) {
        commentDAO.deleteComment(id);
    }
}
