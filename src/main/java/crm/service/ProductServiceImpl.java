package crm.service;

import crm.dao.ProductDAO;
import crm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    @Transactional
    public List<Product> getProducts() {
        return productDAO.getProducts();
    }

    @Override
    @Transactional
    public void saveProduct(Product product) {
        productDAO.saveProduct(product);
    }

    @Override
    @Transactional
    public Product getProduct(int id) {
        return productDAO.getProduct(id);
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }

    @Override
    @Transactional
    public List<Product> findProducts(String tempProductName) {
        return productDAO.findProduct(tempProductName);
    }
}





