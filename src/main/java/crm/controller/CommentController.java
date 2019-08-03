package crm.controller;

import java.util.ArrayList;
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
	
	//private Map<String, String> allProducts;
	

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
		
		// 1st position is an Entity object, 2nd position will be shown online
		Map<String,String> productNamesList = new HashMap<>();
		
		List<Product> allProducts = productService.getProducts();
		
		if (allProducts.isEmpty()) { 
			//System.out.println("allProducts == null ");
			return null;
			}
		else { 
			//System.out.println("allProducts is NOT null");
			
			for(Product tempProduct : allProducts) {
				//System.out.println("   >>> " + tempProduct.getName());
				productNamesList.put(tempProduct.getName(), tempProduct.getName());
			}
		}
		//System.out.println("Map<String,String> productNamesList = " + productNamesList);
		return productNamesList;		
	}
	
	
	@GetMapping("/list")
	public String listComments(Model theModel) {
		
		// get comments from the service
		List<Comment> theComments = commentService.getComments();
				
		// add comments to the model
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
	public String showFormForAdd(
			@ModelAttribute("comment") Comment theComment,
			Model theModel) {


		theModel.addAttribute("comment", new Comment());
		theModel.addAttribute("product", new Product());
		
		return "comment-new-form";
	}
	
	@RequestMapping(value = "/saveNewComment", method = RequestMethod.POST)
	public String saveNewComment (
			@ModelAttribute("comment") Comment theComment,
			ModelMap theModelMap, 
			BindingResult theBindingResult) {

		System.out.println(">>> inside 'saveNewComment'");

		// step 1 - validation - checking for empty comment
		String tempCommentDesc = theComment.getCommentDesc();
		if (tempCommentDesc == null) {
			System.out.println(">>> theComment = " + tempCommentDesc + "Please write a comment.");
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
			//System.out.println(">>> Product not selected!!!");
			theModelMap.addAttribute("comment", theComment);
			theModelMap.addAttribute("product", new Product());
			theModelMap.addAttribute("newCommentError", "Please select product.");
			return "comment-new-form";	
		}
		else {
			//System.out.println(">>> Product selected, tempProductName = " + tempProductName);
			theModelMap.addAttribute("product.name", tempProductName);
		}
		
		// step 3.2 - now, we can find productId
		int tempProductId = 0;
				
		List<Product> foundProducts = productService.findProducts(theComment.getProduct().getName());
		if (foundProducts.isEmpty()) { 
			//System.out.println("foundProducts == null ");
			theModelMap.addAttribute("comment", theComment);
			theModelMap.addAttribute("product", new Product());
			theModelMap.addAttribute("newCommentError", "Product doesn't exist in DB.");
			return "comment-new-form";	
		}
		else { System.out.println("foundProducts is NOT null"); 
		   for(Product p : foundProducts) {
			   tempProductId = p.getId();
			   //System.out.println("   >>> tempProductId = " + tempProductId);
		   }
		}
						
		// step 4 - select Product from productId and finally save our comment
		Product tempProduct = productService.getProduct(tempProductId);
		//System.out.println(">>> tempProduct = " + tempProduct);
		
		if (theBindingResult.hasErrors()) {
			return "comment-new-form";
		}
		else {
			theComment.setCreated(onCreate());
			theComment.setLastUpdate(onUpdate());
			theComment.setProduct(tempProduct);
			
			//System.out.println("### >>> theComment = " + theComment);
			commentService.saveComment(theComment);
			return "redirect:/comments/list";
		}	
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(
			@RequestParam("commentId") int theId,
			@ModelAttribute("comment") Comment theComment,
			Model theModel) {	
		
		//System.out.println("#############################");
		//System.out.println(">>> showFormForUpdate > theId = " + theId); // 1st YES
		//System.out.println(">>> showFormForUpdate > theComment = " + theComment); // 1st NULL
		
		Comment theTempComment = commentService.getComment(theId);
		//System.out.println(">>> showFormForUpdate > theTempComment = " + theTempComment); // 1st YES
		theModel.addAttribute("comment", theTempComment);
		
		List<Product> theTempProducts = productService.getProducts();
		theModel.addAttribute("product", theTempProducts);
		
		//System.out.println(">>> showFormForUpdate DONE");
		
		return "comment-update-form";
	}
	
	@PostMapping("/saveUpdatedComment")
	public String saveUpdatedComment(
			@Valid @ModelAttribute("comment") Comment theComment,
			BindingResult theBindingResult,
			Model theModel) {
		
		//System.out.println("#############################");
		//System.out.println(">>> saveUpdatedComment > theComment = " + theComment); // 1st only ID
		
		// step 1 - validation - checking for empty comment
		String tempCommentDesc = theComment.getCommentDesc();
		if (tempCommentDesc == null) {
			//System.out.println(">>> theComment = " + tempCommentDesc + "Please write a comment.");
			theModel.addAttribute("comment", theComment);
			theModel.addAttribute("product", new Product());
			theModel.addAttribute("updatedCommentError", "Please write a comment.");
			return "comment-update-form";	
		}
		
		//System.out.println(">>> saveUpdatedComment START");

		Comment theOldComment = commentService.getComment(theComment.getId());
		
		//System.out.println(">>> theComment = " + theComment); // has only .id and new .commentDesc
		//System.out.println(">>> theOldComment = " + theOldComment);
		
		// step 1 - setting new date for .lastUpdate
		theComment.setLastUpdate(onUpdate());
		//System.out.println(">>> 1. theComment = " + theComment);
		
		// step 2 - copying all missing data from theOldComment to theComment
		
		// step 2.1 - created
		theComment.setCreated(theOldComment.getCreated());
		//System.out.println(">>> 2. theComment = " + theComment);
		
		// step 2.2 - userId
		theComment.setUserId(theOldComment.getUserId());
		//System.out.println(">>> 3. theComment = " + theComment);
		
		// step 2.3 - productId
		int tempProductId = theOldComment.getProduct().getId();
		//System.out.println(">>> tempProductId = " + tempProductId);
		
		Product tempProduct = productService.getProduct(tempProductId);
		//System.out.println(">>> tempProduct = " + tempProduct);
		theComment.setProduct(tempProduct);
		
		// step 3 - preview for theComment before saving it & save
		//System.out.println(">>> theComment = " + theComment);
		
		if (theBindingResult.hasErrors()) {
			//System.out.println("theBindingResult = " + theBindingResult);
			return "comment-update-form";
		}
		else {
			commentService.saveComment(theComment);
			return "redirect:/comments/list";
		}	
	}	
	
	@GetMapping("/delete")
	public String deleteComment(@RequestParam("commentId") int theId) {
		//System.out.println("### Controller 1");
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
            	//System.out.println("### inside getCurrentUserId id1 = " + id);
            }
                
            else if (authentication.getPrincipal() instanceof String) {
            	id = (String) authentication.getPrincipal();
            	//System.out.println("### inside getCurrentUserId id2 = " + id);
            }
                
        try {
            return Long.valueOf(id != null ? id : "1"); //anonymoususer
        } catch (NumberFormatException e) {
            return 1L;
        }
	}
}









