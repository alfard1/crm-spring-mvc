package crm.dao;

import crm.entity.Comment;

import java.util.List;

public interface CommentDAO {

    List<Comment> getComments();

    void newComment(Comment comment);

    void updateComment(Comment comment);

    Comment getComment(int id);

    void deleteComment(int id);
}
