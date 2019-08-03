package crm.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import crm.entity.Comment;
import crm.service.CommentService;
import crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import crm.entity.Product;
import crm.service.ProductService;
import crm.user.CrmUser;

@Controller
@RequestMapping("/product")
public class ProductController {

	
	// add an initbinder to remove all whitespaces from strings comeing via controller from beginning and end of string
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// need to inject our services we need for Comments details
	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;
	
	@GetMapping("/list")
	public String listProducts(Model theModel) {
		
		// get customers from the service
		List<Product> theProducts = productService.getProducts();

		// TEST FOR DEBUGGING: printing all products in the console
		/*
			if (theProducts.isEmpty()) { System.out.println("theProducts == null "); }
			else { System.out.println("theProducts is NOT null");
				for(Product tempProduct : theProducts) {
					System.out.println("# Product Id = " + tempProduct.getId());
					System.out.println("# Product Name = " + tempProduct.getName());
					System.out.println("# Product Comments = " + productService.getProduct(tempProduct.getId()).getComments());
					System.out.println("   >>> tempProduct = " + productService.getProduct(tempProduct.getId()));
				}
			}
		*/

		// add the customers to the model
		theModel.addAttribute("products", theProducts);

		return "products";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		theModel.addAttribute("product", new Product());
		return "product-form";
	}

	@PostMapping("/saveProduct")
	public String saveProduct(
			@Valid @ModelAttribute("product") Product theProduct, 
			BindingResult theBindingResult,
			Model theModel) {
		
		String tempProductName = theProduct.getName();
		//System.out.println("tempProductName = " + tempProductName);

		if (productService.findProducts(tempProductName).isEmpty()) {
			System.out.println("Product with the name '" + tempProductName + "' doesn't exist, you can create it.");
		}
		else {
			System.out.println("Product with the name '" + tempProductName + "' already exists!!!");
			theModel.addAttribute("product", theProduct);
			theModel.addAttribute("newProductError", "Product already exists.");
			return "product-form";
		}

		// checking validation (empty product name)
		if (theBindingResult.hasErrors()) {
			theModel.addAttribute("product", theProduct);
			System.out.println(theBindingResult);
			return "product-form";
		}
		
		productService.saveProduct(theProduct);
		return "redirect:/product/list";
		
	}
	
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(
			@RequestParam("productId") int theId,
			//@ModelAttribute("product") Product theProduct,
			Model theModel) {
		
		// get the product from our service
		//System.out.println("### >>> 1 START");
		Product theProduct = productService.getProduct(theId);	
		//System.out.println("### >>> 1 END");
		// set product as a model attribute to pre-populate the form
		theModel.addAttribute("product", theProduct);
		
		// send over to our form		
		return "product-form";
	}
	
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam("productId") int theId) {
		
		// delete the product
		productService.deleteProduct(theId);
		
		return "redirect:/product/list";
	}
}










