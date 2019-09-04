package crm.dao;

import crm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    // TODO: it works only if in DB there is 1 object, for more code should be improved
    @Override
    public User findByUserName(String userName) {

        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User where userName=:uName", User.class);
        query.setParameter("uName", userName);
        User user = null;

        // if 1 user exists in DB code below works without exception and return user found in DB
        // when exception appears (no user in DB) this run code from 'catch'
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    @Override
    public void save(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(user);
    }
}
