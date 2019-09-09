package crm.dao;

import crm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUserName(String userName) {
        Query query = entityManager.createQuery("from User");
        List<User> users = query.getResultList();
        if (users.isEmpty()) return null;
        else for (User tempUser : users) {
            if (tempUser.getUserName().equals(userName)) {
                return entityManager.find(User.class, tempUser.getId());
            }
        }
        return null;
    }

    @Override
    public void save(User user) {
        User dbUser = entityManager.merge(user);
        user.setId(dbUser.getId());
    }
}
