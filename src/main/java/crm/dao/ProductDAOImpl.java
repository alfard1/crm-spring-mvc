package crm.dao;

import crm.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Product> getProducts() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Product> query = currentSession.createQuery("from Product order by name", Product.class);
        return query.getResultList();
    }

    @Override
    public Product getProduct(int id) {

        Session currentSession = sessionFactory.getCurrentSession();
        Product tempProduct = new Product();

        //TODO: I added @SuppressWarnings to temporary remove warnings, should add checking object before casting down
        // discussion here: https://stackoverflow.com/questions/367626/how-do-i-fix-the-expression-of-type-list-needs-unchecked-conversion

        @SuppressWarnings("unchecked")
        List<Product> l = currentSession.createQuery(
                "SELECT e FROM Product e JOIN FETCH e.comments where e.id=:productId").setParameter("productId", id)
                .getResultList();

        if (l.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<Product> m = currentSession.createQuery(
                    "SELECT e FROM Product e where e.id=:productId").setParameter("productId", id)
                    .getResultList();
            for (Product p : m) {
                printResult(p); // TODO: remove this line and check adding comments for product without any comment
                tempProduct = p;
            }
        } else {
            for (Product p : l) {
                printResult(p);
                tempProduct = p;
            }
        }
        return tempProduct;
    }

    private static void printResult(Object result) {
        if (result == null) {
            System.out.print("NULL");
        } else if (result instanceof Object[]) {
            Object[] row = (Object[]) result;
            System.out.print("[");
            for (Object o : row) {
                printResult(o);
            }
            System.out.print("]");
        } else if (result instanceof Long || result instanceof Double
                || result instanceof String) {
            System.out.print(result.getClass().getName() + ": " + result);
        } else {
            System.out.print(result);
        }
        System.out.println();
    }

    @Override
    public void saveProduct(Product product) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(product);
    }

    @Override
    public void deleteProduct(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Product product = getProduct(id);
        currentSession.delete(product);
    }

    @Override
    public List<Product> findProduct(String tempProductName) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (List<Product>) currentSession.createQuery(
                "SELECT e FROM Product e where e.name=:productName")
                .setParameter("productName", tempProductName)
                .getResultList();
    }
}
