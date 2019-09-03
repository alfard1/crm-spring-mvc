package crm.controller;

import crm.entity.Product;
import crm.service.ProductService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	// add an initbinder to remove all whitespaces from strings comeing via controller from beginning and end of string
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/list")
	public String listProducts(Model theModel) {

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
	public String showFormForUpdate(@RequestParam("productId") int theId, Model theModel) {
		Product theProduct = productService.getProduct(theId);
		theModel.addAttribute("product", theProduct);
		return "product-form";
	}
	
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam("productId") int theId) {
		productService.deleteProduct(theId);
		return "redirect:/product/list";
	}
}
