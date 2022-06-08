package project.commands;

import project.entities.Check;
import project.entities.DTO.CheckProductsDTO;
import project.entities.User;
import project.manager.Config;
import project.service.CheckService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CommandViewCheck implements ICommand{
    private final CheckService checkService = new CheckService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        // get check_id. attr used when creating check, param when viewing receipt from hyperlink
        int check_id;
        String s_check_id = request.getParameter("checkid");
        if (s_check_id == null) {
            if (request.getAttribute("checkid") == null) {
                request.setAttribute("message", "No receipt id provided to view it.");
                return Config.getInstance().getProperty(Config.ERROR);
            }
            check_id = (int) request.getAttribute("checkid");

        } else {
            check_id = Integer.parseInt(s_check_id);
        }
        Check check = checkService.getCheck(check_id);
        request.setAttribute("access", true);
        // get check products
        List<CheckProductsDTO> products = checkService.constructCheckProductsDTO(check_id);
        request.setAttribute("products", products);
        request.setAttribute("check", check);
        return Config.getInstance().getProperty(Config.VIEWRECEIPT);
    }
}
