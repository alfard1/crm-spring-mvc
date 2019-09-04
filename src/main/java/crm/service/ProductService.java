package crm.service;

import crm.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    void saveProduct(Product product);

    Product getProduct(int id);

    void deleteProduct(int id);

    List<Product> findProducts(String tempProductName);
}
