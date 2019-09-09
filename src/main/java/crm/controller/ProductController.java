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
    public String listProducts(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model) {

        String tempProductName = product.getName();
        if (!productService.findProducts(tempProductName).isEmpty()) {
            model.addAttribute("product", product);
            model.addAttribute("newProductError", "Product already exists.");
            return "product-form";
        }

        // checking validation (empty product name)
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "product-form";
        }

        productService.saveProduct(product);
        return "redirect:/product/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("productId") int id, Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "product-form";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("productId") int id) {
        productService.deleteProduct(id);
        return "redirect:/product/list";
    }
}
