package project.dao.interfaces;

import project.entities.Permission;
import project.entities.Role;

import java.sql.SQLException;
import java.util.List;

public interface IPermissionDAO {
    Permission getPermission(int id) throws SQLException;
    List<Permission> getRolePermissions(Role role) throws SQLException;
}
