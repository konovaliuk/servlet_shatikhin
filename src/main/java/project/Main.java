package project;

import project.entities.Permission;
import project.entities.Role;
import project.entities.User;
import project.entities.enums.Permissions;
import project.service.PermissionService;
import project.service.RoleService;
import project.service.UserService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        RoleService roleService = new RoleService();
        PermissionService permissionService = new PermissionService();
        String username = "bpage";
        Permissions perm = Permissions.add_product_check;
        User user = userService.find(username);
        System.out.println("id for user " + username + " is " + user.getId());
        Role user_role = roleService.getUserRole(user);
        System.out.println("role is " + user_role.getName());
        List<Permission> user_perms = permissionService.getRolePermissions(user_role);
        for (Permission p : user_perms) {
            System.out.println("user has perm " + p.getName());
        }
        boolean access = permissionService.checkPermission(user, perm);
        System.out.println("can user access " + perm.name() + "? " + access);
    }
}
