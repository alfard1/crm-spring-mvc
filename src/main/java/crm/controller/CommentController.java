package crm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import crm.entity.Comment;
import crm.entity.Product;
import crm.service.CommentService;
import crm.service.ProductService;
import crm.service.UserService;

@Controller
@RequestMapping("/comments")
public class CommentController {
	
	// add an initbinder to remove all whitespaces from strings coming via controller
	// from beginning and end of the string
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	// add methods for setting dates (create and update product)
	@PrePersist
	protected static Date onCreate() {
		Date created = new Date();
		return created;
	}

	@PreUpdate
	protected static Date onUpdate() {
		Date updated = new Date();
	    return updated; 
	}

	// need to inject our services we need for Comments details
	@Autowired
	private CommentService commentService;
	
	@Autowired
    private UserService userService;

	@Autowired
	private ProductService productService;

	@ModelAttribute("productNamesList")
	public Map<String, String> getProductNamesList() {
		
		// 1st String is an Entity object name, 2nd String will be shown online
		Map<String,String> productNamesList = new HashMap<>();
		
		List<Product> allProducts = productService.getProducts();

		if (!allProducts.isEmpty()) {
			for(Product tempProduct : allProducts) {
				productNamesList.put(tempProduct.getName(), tempProduct.getName());
			}
		} else return null;
		return productNamesList;		
	}

	@GetMapping("/list")
	public String listComments(Model theModel) {

		List<Comment> theComments = commentService.getComments();
		theModel.addAttribute("comments", theComments);

		// TEST FOR DEBUGGING: printing all comments in the console
		/*
			if (theComments.isEmpty()) { System.out.println("theComments == null "); }
			else { System.out.println("theComments is NOT null");
				for(Comment tempComment : theComments) {
					System.out.println("# Comment Id = " + tempComment.getId());
					System.out.println("   >>> tempComment = " + tempComment);
				}
			}
		*/

		return "comments";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(@ModelAttribute("comment") Comment theComment, Model theModel) {
		theModel.addAttribute("comment", new Comment());
		theModel.addAttribute("product", new Product());
		return "comment-new-form";
	}
	
	@RequestMapping(value = "/saveNewComment", method = RequestMethod.POST)
	public String saveNewComment (
			@ModelAttribute("comment") Comment theComment,
			ModelMap theModelMap, 
			BindingResult theBindingResult) {

		// step 1 - validation - checking for empty comment
		String tempCommentDesc = theComment.getCommentDesc();
		if (tempCommentDesc == null) {
			theModelMap.addAttribute("comment", theComment);
			theModelMap.addAttribute("product", new Product());
			theModelMap.addAttribute("newCommentError", "Please write a comment.");
			return "comment-new-form";	
		}
				
		// step 2 - get User Name and User Id from the session		
		String tempUserName = getCurrentUserName();
		crm.entity.User existing = userService.findByUserName(tempUserName);		
		theComment.setUserId(existing.getId());

		// step 3 - take productId from the form
		// step 3.1 - in memory we have only name of the product, without product.id, let's take product name
		
		String tempProductName = theComment.getProduct().getName();
		if (tempProductName == null) {
			theModelMap.addAttribute("comment", theComment);
			theModelMap.addAttribute("product", new Product());
			theModelMap.addAttribute("newCommentError", "Please select product.");
			return "comment-new-form";	
		}
		else {
			theModelMap.addAttribute("product.name", tempProductName);
		}
		
		// step 3.2 - now, we can find productId
		int tempProductId = 0;
		List<Product> foundProducts = productService.findProducts(theComment.getProduct().getName());
		if (foundProducts.isEmpty()) {
			theModelMap.addAttribute("comment", theComment);
			theModelMap.addAttribute("product", new Product());
			theModelMap.addAttribute("newCommentError", "Product doesn't exist in DB.");
			return "comment-new-form";	
		}
		else for (Product p : foundProducts) tempProductId = p.getId();

		Product tempProduct = productService.getProduct(tempProductId);
		
		if (theBindingResult.hasErrors()) return "comment-new-form";
		else {
			theComment.setCreated(onCreate());
			theComment.setLastUpdate(onUpdate());
			theComment.setProduct(tempProduct);
			commentService.saveComment(theComment);
			return "redirect:/comments/list";
		}	
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("commentId") int theId, @ModelAttribute("comment") Comment theComment,
			Model theModel) {

		Comment theTempComment = commentService.getComment(theId);
		theModel.addAttribute("comment", theTempComment);
		List<Product> theTempProducts = productService.getProducts();
		theModel.addAttribute("product", theTempProducts);
		return "comment-update-form";
	}
	
	@PostMapping("/saveUpdatedComment")
	public String saveUpdatedComment(@Valid @ModelAttribute("comment") Comment theComment, BindingResult theBindingResult,
			Model theModel) {
		
		// step 1 - validation - checking for empty comment
		String tempCommentDesc = theComment.getCommentDesc();
		if (tempCommentDesc == null) {
			theModel.addAttribute("comment", theComment);
			theModel.addAttribute("product", new Product());
			theModel.addAttribute("updatedCommentError", "Please write a comment.");
			return "comment-update-form";	
		}
		
		Comment theOldComment = commentService.getComment(theComment.getId());

		// step 1 - setting new date for .lastUpdate
		theComment.setLastUpdate(onUpdate());

		// step 2 - copying all missing data from theOldComment to theComment
		theComment.setCreated(theOldComment.getCreated());
		theComment.setUserId(theOldComment.getUserId());
		int tempProductId = theOldComment.getProduct().getId();
		Product tempProduct = productService.getProduct(tempProductId);
		theComment.setProduct(tempProduct);
		
		// step 3 - preview for theComment before saving it & save
		if (theBindingResult.hasErrors()) return "comment-update-form";
		else {
			commentService.saveComment(theComment);
			return "redirect:/comments/list";
		}	
	}	
	
	@GetMapping("/delete")
	public String deleteComment(@RequestParam("commentId") int theId) {
		commentService.deleteComment(theId);
		return "redirect:/comments/list";
	}
	
	String getCurrentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	Long getCurrentUserId() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String id = null;
        if (authentication != null)
            if (authentication.getPrincipal() instanceof UserDetails) {
            	id = ((UserDetails) authentication.getPrincipal()).getUsername();
            }
            else if (authentication.getPrincipal() instanceof String) {
            	id = (String) authentication.getPrincipal();
            }
                
        try {
            return Long.valueOf(id != null ? id : "1"); //anonymoususer
        } catch (NumberFormatException e) {
            return 1L;
        }
	}
}
