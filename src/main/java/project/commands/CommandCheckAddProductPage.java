package project.commands;

import project.entities.Product;
import project.entities.User;
import project.manager.Config;
import project.service.CheckService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CommandCheckAddProductPage implements ICommand {
    private final CheckService checkService = new CheckService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        String s_check_id = request.getParameter("checkid");
        if (s_check_id == null) {
            request.setAttribute("message", "No receipt id provided to add products to.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        int check_id = Integer.parseInt(s_check_id);
        if (!checkService.isCheckEditable(check_id)) {
            request.setAttribute("message", "Receipt is closed.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        List<Product> products = checkService.getAvailableProducts(check_id);
        request.setAttribute("access", true);
        request.setAttribute("checkid", check_id);
        request.setAttribute("productList", products);
        return Config.getInstance().getProperty(Config.CHECKADDPROD);
    }
}
