package crm.service;

import java.util.List;

import crm.entity.Product;

public interface ProductService {

	List<Product> getProducts();

	void saveProduct(Product theProduct);

	Product getProduct(int theId);

	void deleteProduct(int theId);
	
	List<Product> findProducts(String tempProductName);
}
