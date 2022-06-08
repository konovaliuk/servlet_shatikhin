package project.commands;

import project.entities.Role;
import project.entities.User;
import project.manager.Config;
import project.service.RoleService;
import project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandOpenAdmin implements ICommand{
    private final UserService userService = new UserService();
    private final RoleService roleService = new RoleService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        Role user_role = roleService.getUserRole(user);
        request.setAttribute("access", false);
        if (roleService.isRoleAdmissible(user_role, Config.ACCESS_ADMIN)) {
            request.setAttribute("access", true);
            request.setAttribute("userList", userService.getAllUsers());
            return Config.getInstance().getProperty(Config.ADMIN);
        }
        request.setAttribute("message", "No access to admin page");
        return Config.getInstance().getProperty(Config.ERROR);
    }
}
