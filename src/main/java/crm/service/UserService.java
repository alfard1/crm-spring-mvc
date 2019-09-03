package crm.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import crm.entity.User;
import crm.user.CrmUser;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);
    void save(CrmUser crmUser);
}
