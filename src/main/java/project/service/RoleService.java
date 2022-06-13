package project.service;

import project.dao.DAOFactory;
import project.entities.Role;
import project.entities.User;
import project.entities.enums.Roles;

import java.sql.SQLException;
import java.util.EnumSet;
import java.util.List;

public class RoleService {
    public Role getUserRole(int user_id) {
        UserService userService = new UserService();
        return getUserRole(userService.find(user_id));
    }
    public Role getUserRole(User user) {
        int roleid = user.getRole_id();
        try {
            return DAOFactory.getRoleDAO().getRole(roleid);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Role> getAllRoles() {
        try {
            return DAOFactory.getRoleDAO().getAllRoles();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Roles roleToEnum(Role role) {
        return Roles.valueOf(role.getName());
    }
    public boolean isRoleAdmissible(Role role, EnumSet<Roles> roles) {
        return roles.contains(roleToEnum(role));
    }
}
