package project.controller;

import project.commands.ICommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class Controller extends HttpServlet {
    ControllerHelper helper = ControllerHelper.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        try {
            ICommand command = helper.getCommand(request);
            page = command.execute(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (request.getParameter("command") != null) {
            if (request.getParameter("command").equals("logout")) {
                response.sendRedirect("/");
                return;
            }
        }
        request.getRequestDispatcher(page).forward(request, response);
    }
}