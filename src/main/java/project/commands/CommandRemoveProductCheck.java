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

public class CommandRemoveProductCheck implements ICommand {
    private final CheckService checkService = new CheckService();
    private final PermissionService permissionService = new PermissionService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        if (!permissionService.checkPermission(user, Permissions.delete_product_check)) {
            request.setAttribute("message", "You lack " + Permissions.delete_product_check.name() + " permission to do this.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        String s_check_id = request.getParameter("checkid");
        String s_prod_id = request.getParameter("prodid");
        if (s_check_id == null || s_prod_id == null) {
            request.setAttribute("message", "No id provided.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        int check_id = Integer.parseInt(s_check_id);
        int prod_id = Integer.parseInt(s_prod_id);
        boolean result = checkService.checkRemoveProduct(check_id, prod_id);
        if (!result) {
            request.setAttribute("message", "Error removing product " + prod_id + " from receipt " + check_id);
            return Config.getInstance().getProperty(Config.ERROR);
        }
        request.setAttribute("checkid", check_id);
        return new CommandViewCheck().execute(request, response);
    }
}
