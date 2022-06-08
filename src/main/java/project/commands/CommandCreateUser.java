package project.commands;

import project.entities.User;
import project.entities.enums.Permissions;
import project.manager.Config;
import project.service.PermissionService;
import project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandCreateUser implements ICommand {
    private final UserService userService = new UserService();
    private final PermissionService permissionService = new PermissionService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        if (!permissionService.checkPermission(user, Permissions.all)) {
            request.setAttribute("message", "You lack " + Permissions.all.name() + " permission to do this.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        int role_id = Integer.parseInt(request.getParameter("role"));
        User newUser = new User(username, password, fullname, role_id);
        int id = userService.createUser(newUser);
        if (id == -1) {
            request.setAttribute("message", "Error creating user.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        return new CommandOpenAdmin().execute(request, response);
    }
}
