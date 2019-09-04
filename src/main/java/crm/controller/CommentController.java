package crm.controller;

import crm.entity.Comment;
import crm.entity.Product;
import crm.service.CommentService;
import crm.service.ProductService;
import crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

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
        return new Date();
    }

    @PreUpdate
    protected static Date onUpdate() {
        return new Date();
    }

    @ModelAttribute("productNamesList")
    public Map<String, String> getProductNamesList() {

        // 1st String is an Entity object name, 2nd String will be shown online
        Map<String, String> productNamesList = new HashMap<>();

        List<Product> allProducts = productService.getProducts();

        if (!allProducts.isEmpty()) {
            for (Product tempProduct : allProducts) productNamesList.put(tempProduct.getName(), tempProduct.getName());
        } else return null;
        return productNamesList;
    }

    @GetMapping("/list")
    public String listComments(Model model) {

        List<Comment> comments = commentService.getComments();
        model.addAttribute("comments", comments);

        // TEST FOR DEBUGGING: printing all comments in the console
		/*
			if (comments.isEmpty()) { System.out.println("comments == null "); }
			else { System.out.println("comments is NOT null");
				for(Comment tempComment : comments) {
					System.out.println("# Comment Id = " + tempComment.getId());
					System.out.println("   >>> tempComment = " + tempComment);
				}
			}
		*/

        return "comments";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(@ModelAttribute("comment") Comment comment, Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("product", new Product());
        return "comment-new-form";
    }

    @PostMapping("/saveNewComment")
    public String saveNewComment(
            @ModelAttribute("comment") Comment comment,
            ModelMap modelMap,
            BindingResult bindingResult) {

        // step 1 - validation - checking for empty comment
        String tempCommentDesc = comment.getCommentDesc();
        if (tempCommentDesc == null) {
            modelMap.addAttribute("comment", comment);
            modelMap.addAttribute("product", new Product());
            modelMap.addAttribute("newCommentError", "Please write a comment.");
            return "comment-new-form";
        }

        // step 2 - get User Name and User Id from the session
        String tempUserName = getCurrentUserName();
        crm.entity.User existing = userService.findByUserName(tempUserName);
        comment.setUserId(existing.getId());

        // step 3 - take productId from the form
        // step 3.1 - in memory we have only name of the product, without product.id, let's take product name

        String tempProductName = comment.getProduct().getName();
        if (tempProductName == null) {
            modelMap.addAttribute("comment", comment);
            modelMap.addAttribute("product", new Product());
            modelMap.addAttribute("newCommentError", "Please select product.");
            return "comment-new-form";
        } else {
            modelMap.addAttribute("product.name", tempProductName);
        }

        // step 3.2 - now, we can find productId
        int tempProductId = 0;
        List<Product> foundProducts = productService.findProducts(comment.getProduct().getName());
        if (foundProducts.isEmpty()) {
            modelMap.addAttribute("comment", comment);
            modelMap.addAttribute("product", new Product());
            modelMap.addAttribute("newCommentError", "Product doesn't exist in DB.");
            return "comment-new-form";
        } else for (Product p : foundProducts) tempProductId = p.getId();

        Product tempProduct = productService.getProduct(tempProductId);

        if (bindingResult.hasErrors()) return "comment-new-form";
        else {
            comment.setCreated(onCreate());
            comment.setLastUpdate(onUpdate());
            comment.setProduct(tempProduct);
            commentService.saveComment(comment);
            return "redirect:/comments/list";
        }
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("commentId") int id, @ModelAttribute("comment") Comment comment,
                                    Model model) {

        Comment tempComment = commentService.getComment(id);
        model.addAttribute("comment", tempComment);
        List<Product> tempProducts = productService.getProducts();
        model.addAttribute("product", tempProducts);
        return "comment-update-form";
    }

    @PostMapping("/saveUpdatedComment")
    public String saveUpdatedComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult bindingResult,
                                     Model model) {

        // step 1 - validation - checking for empty comment
        String tempCommentDesc = comment.getCommentDesc();
        if (tempCommentDesc == null) {
            model.addAttribute("comment", comment);
            model.addAttribute("product", new Product());
            model.addAttribute("updatedCommentError", "Please write a comment.");
            return "comment-update-form";
        }

        Comment oldComment = commentService.getComment(comment.getId());

        // step 1 - setting new date for .lastUpdate
        comment.setLastUpdate(onUpdate());

        // step 2 - copying all missing data from oldComment to comment
        comment.setCreated(oldComment.getCreated());
        comment.setUserId(oldComment.getUserId());
        int tempProductId = oldComment.getProduct().getId();
        Product tempProduct = productService.getProduct(tempProductId);
        comment.setProduct(tempProduct);

        // step 3 - preview for comment before saving it & save
        if (bindingResult.hasErrors()) return "comment-update-form";
        else {
            commentService.saveComment(comment);
            return "redirect:/comments/list";
        }
    }

    @GetMapping("/delete")
    public String deleteComment(@RequestParam("commentId") int id) {
        commentService.deleteComment(id);
        return "redirect:/comments/list";
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    /*
    Long getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String id = null;
        if (authentication != null)
            if (authentication.getPrincipal() instanceof UserDetails) {
                id = ((UserDetails) authentication.getPrincipal()).getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                id = (String) authentication.getPrincipal();
            }

        try {
            return Long.valueOf(id != null ? id : "1"); //anonymoususer
        } catch (NumberFormatException e) {
            return 1L;
        }
    }
    */
}
