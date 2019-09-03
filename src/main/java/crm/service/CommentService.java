package crm.service;

import java.util.List;

import crm.entity.Comment;

public interface CommentService {

	List<Comment> getComments();

	void saveComment(Comment theComment);

	Comment getComment(int theId);

	void deleteComment(int theId);
}
