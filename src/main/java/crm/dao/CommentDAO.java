package crm.dao;

import java.util.List;

import crm.entity.Comment;

public interface CommentDAO {

	List<Comment> getComments();

	void saveComment(Comment theComment);

	Comment getComment(int theId);

	void deleteComment(int theId);
}
