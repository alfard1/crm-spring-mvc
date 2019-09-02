package crm.dao;

import java.util.List;

import crm.entity.Product;

public interface ProductDAO {

	List<Product> getProducts();

	void saveProduct(Product theProduct);

	Product getProduct(int theId);

	void deleteProduct(int theId);
	
	List<Product> findProduct(String theName);
	
}
