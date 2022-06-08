package project.commands;

import project.entities.User;
import project.entities.enums.Permissions;
import project.manager.Config;
import project.service.CheckService;
import project.service.PermissionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandCreateCheck implements ICommand{
    private final CheckService checkService = new CheckService();
    private final PermissionService permissionService = new PermissionService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        if (!permissionService.checkPermission(user, Permissions.open_check)) {
            request.setAttribute("message", "You lack " + Permissions.open_check.name() + " permission to do this.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        int id = checkService.createCheck(user.getId());
        if (id == -1) {
            request.setAttribute("message", "Error opening receipt.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        request.setAttribute("checkid", id);
        return new CommandViewCheck().execute(request, response);
    }
}
