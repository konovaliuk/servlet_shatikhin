package project.commands;

import project.entities.Role;
import project.entities.User;
import project.entities.enums.Permissions;
import project.manager.Config;
import project.service.PermissionService;
import project.service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandCreateUserPage implements ICommand {
    private final RoleService roleService = new RoleService();
    private final PermissionService permissionService = new PermissionService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        if (!permissionService.checkPermission(user, Permissions.all)) {
            request.setAttribute("message", "You lack " + Permissions.all + " permission to do this.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        request.setAttribute("access", true);
        request.setAttribute("roles", roleService.getAllRoles());
        return Config.getInstance().getProperty(Config.CREATEUSER);
    }
}
