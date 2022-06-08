package project.commands;

import project.entities.Check;
import project.entities.DTO.CheckProductsDTO;
import project.entities.User;
import project.entities.enums.CheckStatus;
import project.entities.enums.Permissions;
import project.manager.Config;
import project.service.CheckService;
import project.service.PermissionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CommandCheckClose implements ICommand {
    private final CheckService checkService = new CheckService();
    private final PermissionService permissionService = new PermissionService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        if (!permissionService.checkPermission(user, Permissions.close_check)) {
            request.setAttribute("message", "You lack " + Permissions.close_check.name() + " permission to do this.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        int check_id;
        String s_check_id = request.getParameter("checkid");
        if (s_check_id == null) {
            request.setAttribute("message", "No receipt id provided to close it.");
            return Config.getInstance().getProperty(Config.ERROR);
        } else {
            check_id = Integer.parseInt(s_check_id);
        }
        request.setAttribute("access", true);
        boolean result = checkService.checkEditStatus(check_id, CheckStatus.CLOSED);
        if (!result) {
            request.setAttribute("message", "Error editing receipt's status");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        return new CommandViewCheck().execute(request, response);
    }
}
