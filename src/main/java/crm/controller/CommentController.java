package crm.controller;

import crm.entity.Comment;
import crm.entity.Product;
import crm.entity.User;
import crm.service.CommentService;
import crm.service.ProductService;
import crm.service.UserService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    private final UserService userService;

    private final ProductService productService;

    public CommentController(CommentService commentService, UserService userService, ProductService productService) {
        this.commentService = commentService;
        this.userService = userService;
        this.productService = productService;
    }

    // add an initbinder to remove all whitespaces from strings coming via controller
    // from beginning and end of the string
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
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
        return "comments";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(@ModelAttribute("comment") Comment comment, Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("product", new Product());
        return "comment-new-form";
    }

    @PostMapping("/saveNewComment")
    public String saveNewComment(@ModelAttribute("comment") Comment comment,
                                 ModelMap modelMap,
                                 BindingResult bindingResult) {

        if (comment.getCommentDesc() == null) {
            addValidationErrorToModel(modelMap, "Please write a comment.");
            return "comment-new-form";
        }

        String productName = comment.getProduct().getName();
        if (productName == null) {
            addValidationErrorToModel(modelMap, "Please select product.");
            return "comment-new-form";
        }

        List<Product> foundProducts = productService.findProducts(productName);
        if (foundProducts.isEmpty()) {
            addValidationErrorToModel(modelMap, "Product doesn't exist in DB.");
            return "comment-new-form";
        }

        if (bindingResult.hasErrors()) {
            return "comment-new-form";
        }

        Date now = new Date();
        User existingUser = userService.findByUserName(getCurrentUserName());
        Product product = CollectionUtils.lastElement(foundProducts);
        comment.setUserId(existingUser.getId());
        comment.setCreated(now);
        comment.setLastUpdate(now);
        comment.setProduct(product);

        commentService.newComment(comment);

        return "redirect:/comments/list";
    }

    void addValidationErrorToModel(ModelMap modelMap, String errorMsg) {
        modelMap.addAttribute("newCommentError", errorMsg);
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
        comment.setLastUpdate(new Date());

        // step 2 - copying all missing data from oldComment to comment
        comment.setCreated(oldComment.getCreated());
        comment.setUserId(oldComment.getUserId());
        int tempProductId = oldComment.getProduct().getId();
        Product tempProduct = productService.getProduct(tempProductId);
        comment.setProduct(tempProduct);

        // step 3 - preview for comment before saving it & save
        if (bindingResult.hasErrors()) return "comment-update-form";
        else {
            commentService.updateComment(comment);
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
}
