package project.commands;

import project.entities.DTO.CheckProductsDTO;
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

public class CommandCheckEditProductPage implements ICommand{
    private final CheckService checkService = new CheckService();
    private final ProductService productService = new ProductService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        String s_check_id = request.getParameter("checkid");
        String s_prod_id = request.getParameter("pid");
        if (s_check_id == null || s_prod_id == null) {
            request.setAttribute("message", "No id provided.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        int check_id = Integer.parseInt(s_check_id);
        if (!checkService.isCheckEditable(check_id)) {
            request.setAttribute("message", "Receipt is closed.");
            return Config.getInstance().getProperty(Config.ERROR);
        }
        int prod_id = Integer.parseInt(s_prod_id);
        CheckProductsDTO cpdto = checkService.constuctCheckProductDTO(checkService.getCheckProduct(check_id, prod_id));
        request.setAttribute("checkid", check_id);
        request.setAttribute("cpdto", cpdto);
        request.setAttribute("access", true);
        return Config.getInstance().getProperty(Config.VIEWCHECKPROD);
    }
}
