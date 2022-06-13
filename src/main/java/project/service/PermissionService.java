package project.service;

import project.dao.DAOFactory;
import project.entities.Permission;
import project.entities.Role;
import project.entities.User;
import project.entities.enums.Permissions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class PermissionService {
    public boolean checkPermission(User user, Permissions permission) {
        List<Permission> perms = getUserPermissions(user);
        for (Permission p: perms) {
            if (Permissions.valueOf(p.getName()) == Permissions.all) {
                return true;
            }
            if (permission == Permissions.valueOf(p.getName())) {
                return true;
            }
        }
        return false;
    }
    public List<Permission> getUserPermissions (User user) {
        RoleService roleService = new RoleService();
        Role user_role = roleService.getUserRole(user);
        return getRolePermissions(user_role);
    }
    public List<Permission> getRolePermissions (Role role) {
        try {
            return DAOFactory.getPermissionDAO().getRolePermissions(role);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    //
    // do I even need it ?
    //
    private List<Permissions> permissionsToEnum(List<Permission> permissions) {
        List<Permissions> perms = new ArrayList<>();
        for (Permission p : permissions) {
            perms.add(Permissions.valueOf(p.getName()));
        }
        return perms;
    }
    public EnumSet<Permissions> getUserPermissionsEnum(User user) {
        List<Permission> perms = getUserPermissions(user);
        return EnumSet.copyOf(permissionsToEnum(perms));
    }
}
