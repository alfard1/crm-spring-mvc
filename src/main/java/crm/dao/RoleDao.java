package crm.dao;

import crm.entity.Role;

public interface RoleDao {
	Role findRoleByName(String theRoleName);
}
