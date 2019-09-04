package crm.dao;

import crm.entity.Product;

import java.util.List;

public interface ProductDAO {

    List<Product> getProducts();

    void saveProduct(Product product);

    Product getProduct(int id);

    void deleteProduct(int id);

    List<Product> findProduct(String name);

}
