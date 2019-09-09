package crm.dao;

import crm.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    private final EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleByName(String roleName) {
        Query query = entityManager.createQuery("from Role");
        List<Role> roles = query.getResultList();
        if (!roles.isEmpty()) {
            for (Role tempRole : roles) {
                if (tempRole.getName() == roleName) return tempRole;
            }
        }
        return null;
    }
}
