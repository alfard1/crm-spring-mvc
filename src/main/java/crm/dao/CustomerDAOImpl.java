package crm.dao;

import crm.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private final EntityManager entityManager;

    public CustomerDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> getCustomers() {
        Query query = entityManager.createQuery("from Customer order by lastName");
        List<Customer> customers = query.getResultList();
        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        Customer dbCustomer = entityManager.merge(customer);

        // if id=0 then save/insert, else update with id we should get from db
        customer.setId(dbCustomer.getId());
    }

    @Override
    public Customer getCustomer(int id) {
        Customer customer = entityManager.find(Customer.class, id);
        return customer;
    }

    @Override
    public void deleteCustomer(int id) {
        Query query = entityManager.createQuery("delete from Customer where id=:customerId");
        query.setParameter("customerId", id);
        query.executeUpdate();
    }
}
