package crm.service;

import java.util.List;

import crm.entity.Product;

public interface ProductService {

	public List<Product> getProducts();

	public void saveProduct(Product theProduct);

	public Product getProduct(int theId);

	public void deleteProduct(int theId);
	
	public List<Product> findProducts(String tempProductName);
}
