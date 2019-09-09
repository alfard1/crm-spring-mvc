package crm.dao;

import crm.entity.Comment;
import crm.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private final EntityManager entityManager;

    public ProductDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Product> getProducts() {
        Query query = entityManager.createQuery("from Product order by name");
        return (List<Product>) query.getResultList();
    }

    @Override
    public Product getProduct(int id) {
        Product product = entityManager.find(Product.class, id);
        return product;
    }

    @Override
    public void saveProduct(Product product) {
        Product dbProduct = entityManager.merge(product);
        product.setId(dbProduct.getId());
    }

    @Override
    public void deleteProduct(int id) {
        Product product = entityManager.find(Product.class, id);
        List<Comment> comments = product.getComments();
        if (!comments.isEmpty()) {
            for (Comment tempComment : comments) {
                Query query1 = entityManager.createQuery("delete from Comment where id=:theCommentId");
                query1.setParameter("theCommentId", tempComment.getId());
                query1.executeUpdate();
            }
        }
        Query query2 = entityManager.createQuery("delete from Product where id=:theProductId");
        query2.setParameter("theProductId", id);
        query2.executeUpdate();
    }

    @Override
    public List<Product> findProduct(String tempProductName) {
        return (List<Product>) entityManager.createQuery(
                "SELECT e FROM Product e where e.name=:productName")
                .setParameter("productName", tempProductName)
                .getResultList();
    }
}
