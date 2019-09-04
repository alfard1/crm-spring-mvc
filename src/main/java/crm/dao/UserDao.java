package crm.dao;

import crm.entity.User;

public interface UserDao {
    User findByUserName(String userName);

    void save(User user);
}
