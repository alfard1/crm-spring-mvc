package crm.dao;

import java.util.List;

import crm.entity.Product;

public interface ProductDAO {

	public List<Product> getProducts();

	public void saveProduct(Product theProduct);

	public Product getProduct(int theId);

	public void deleteProduct(int theId);
	
	public List<Product> findProduct(String theName);
	
}
