package crm.service;

import crm.dao.CommentDAO;
import crm.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Override
    @Transactional
    public List<Comment> getComments() {
        return commentDAO.getComments();
    }

    @Override
    @Transactional
    public void saveComment(Comment comment) {
        commentDAO.saveComment(comment);
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
