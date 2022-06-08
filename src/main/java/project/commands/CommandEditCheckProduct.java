package project.commands;

import project.entities.User;
import project.manager.Config;
import project.service.CheckService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandEditCheckProduct implements ICommand {
    private final CheckService checkService = new CheckService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        String s_check_id = request.getParameter("checkid");
        String s_prod_id = request.getParameter("prodid");
        if (s_check_id == null || s_prod_id == null) {
            request.setAttribute("message", "No id provided.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        int check_id = Integer.parseInt(s_check_id);
        int prod_id = Integer.parseInt(s_prod_id);
        float qty;
        try {
            qty = Float.parseFloat(request.getParameter("qty"));
        } catch (Exception e) {
            request.setAttribute("message", "Bad input");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        boolean result = checkService.checkSetProduct(check_id, prod_id, qty);
        if (!result) {
            request.setAttribute("message", "Error editing quantity.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        return new CommandViewCheck().execute(request, response);
    }
}
