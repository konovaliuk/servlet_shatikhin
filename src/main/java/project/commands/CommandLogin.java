package project.commands;

import project.manager.Config;
import project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandLogin implements ICommand{
    private final UserService userService = new UserService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        if (userService.checkCredentials(username, password)) {
            request.setAttribute("access", true);
            session.setAttribute("user", userService.find(username));
            return Config.getInstance().getProperty(Config.MAIN);
        }
        return Config.getInstance().getProperty(Config.LOGIN);
    }
}
