package crm.service;

import java.util.List;

import crm.entity.Comment;

public interface CommentService {

	public List<Comment> getComments();

	public void saveComment(Comment theComment);

	public Comment getComment(int theId);

	public void deleteComment(int theId);
}
