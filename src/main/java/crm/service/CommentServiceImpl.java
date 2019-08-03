package crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crm.dao.CommentDAO;
import crm.entity.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	// need to inject comment dao
	@Autowired
	private CommentDAO commentDAO;
	
	@Override
	@Transactional
	public List<Comment> getComments() {
		return commentDAO.getComments();
	}

	@Override
	@Transactional
	public void saveComment(Comment theComment) {

		commentDAO.saveComment(theComment);
	}

	@Override
	@Transactional
	public Comment getComment(int theId) {
		
		return commentDAO.getComment(theId);
	}

	@Override
	@Transactional
	public void deleteComment(int theId) {
		
		commentDAO.deleteComment(theId);
	}
}





