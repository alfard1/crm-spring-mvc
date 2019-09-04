package crm.service;

import crm.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getComments();

    void saveComment(Comment comment);

    Comment getComment(int id);

    void deleteComment(int id);
}
