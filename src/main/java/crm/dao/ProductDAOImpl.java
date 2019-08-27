package crm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crm.entity.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
			
	@Override
	public List<Product> getProducts() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// create a query: sort by last name
		Query<Product> theQuery = currentSession.createQuery("from Product order by name", Product.class);
		
		// execute query and get result list
		List<Product> products = theQuery.getResultList();
				
		// return the results		
		return products;
	}

	@Override
	public Product getProduct(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Product tempProduct = new Product();

		//TODO: I added @SuppressWarnings to temporary remove warnings, should add checking object before casting down
		// discussion here: https://stackoverflow.com/questions/367626/how-do-i-fix-the-expression-of-type-list-needs-unchecked-conversion

		@SuppressWarnings("unchecked")
		List<Product> l = currentSession.createQuery(
		        "SELECT e FROM Product e JOIN FETCH e.comments where e.id=:theProductId").setParameter("theProductId", theId)
		        .getResultList();
		
		
		if(l.isEmpty()) {
			//System.out.println(">>> ProductDAOImpl > getProduct > Product without comments");
			@SuppressWarnings("unchecked")
			List<Product> m = currentSession.createQuery(
			        "SELECT e FROM Product e where e.id=:theProductId").setParameter("theProductId", theId)
			        .getResultList();
			
		    for (Product p : m) {
		    	printResult(p); // TODO: remove this line and check adding comments for product without any comment, sth is wrong
		    	tempProduct = p;
		    }
		}
		else {
			//System.out.println(">>> ProductDAOImpl > getProduct > Product with comments");
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
		      for (int i = 0; i < row.length; i++) {
		        printResult(row[i]);
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
	public void saveProduct(Product theProduct) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/upate product
		currentSession.saveOrUpdate(theProduct);
	}
	  
	@Override
	public void deleteProduct(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Product theProduct = getProduct(theId);
		//System.out.println(">>> Deleting Product = " + theProduct);
		
		currentSession.delete(theProduct);
		//System.out.println(">>> Product with ID = " + theId + " deleted (incl. comments)");
	}

	@Override
	public List<Product> findProduct(String tempProductName) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		List<Product> m = currentSession.createQuery(
				"SELECT e FROM Product e where e.name=:theProductName")
				.setParameter("theProductName", tempProductName)
				.getResultList();

		return m;
	}
}
