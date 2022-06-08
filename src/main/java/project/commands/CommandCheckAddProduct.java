package project.commands;

import project.entities.Product;
import project.entities.User;
import project.manager.Config;
import project.service.CheckService;
import project.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandCheckAddProduct implements ICommand {
    private final ProductService productService = new ProductService();
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
        String prod = request.getParameter("prod");
        if (prod == null) {
            request.setAttribute("message", "Bad input");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        String[] parts = prod.split("-");
        if(parts.length == 0) {
            request.setAttribute("message", "Bad input");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        int prod_id;
        float qty;
        try {
            qty = Float.parseFloat(request.getParameter("qty"));
        } catch (Exception e) {
            request.setAttribute("message", "Bad input");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        try {
            prod_id = Integer.parseInt(parts[0]);
        } catch (Exception e) {
            request.setAttribute("message", "Bad input");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        Product product = productService.getProduct(prod_id);
        boolean result = checkService.checkAddProduct(check_id, prod_id, qty);
        if (!result) {
            request.setAttribute("message", "Error adding product " + prod_id + " to receipt " + check_id);
            return Config.getInstance().getProperty(Config.ERROR);
        }
        request.setAttribute("checkid", check_id);
        return new CommandViewCheck().execute(request, response);
    }
}
