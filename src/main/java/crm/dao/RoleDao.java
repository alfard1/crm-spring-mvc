package crm.dao;

import crm.entity.Role;

public interface RoleDao {
	public Role findRoleByName(String theRoleName);
}
