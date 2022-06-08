package project.commands;

import project.entities.Role;
import project.entities.User;
import project.manager.Config;
import project.service.ProductService;
import project.service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandOpenStorage implements ICommand{
    private final ProductService productService = new ProductService();
    private final RoleService roleService = new RoleService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return Config.getInstance().getProperty(Config.LOGIN);
        Role user_role = roleService.getUserRole(user);
        request.setAttribute("access", false);
        if (roleService.isRoleAdmissible(user_role, Config.ACCESS_STORAGE)) {
            request.setAttribute("access", true);
            request.setAttribute("productList", productService.getProducts());
            return Config.getInstance().getProperty(Config.STORAGE);
        }
        request.setAttribute("message", "No access to storage.");
        return Config.getInstance().getProperty(Config.ERROR);
    }
}
