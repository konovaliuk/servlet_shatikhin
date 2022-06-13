package project.controller;

import project.commands.CommandMissing;
import project.commands.ECommands;
import project.commands.ICommand;
import project.entities.User;

import javax.servlet.http.HttpServletRequest;

public class ControllerHelper {
    private static ControllerHelper instance = null;
    public ICommand getCommand(HttpServletRequest request) {
        String commandName = request.getParameter("command");
        ICommand command;
        try {
            command = ECommands.valueOf(commandName).getCommand();
        }
        catch (Exception e) {
            command = new CommandMissing();
            commandName = "missing";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            System.out.println("Executing command " + commandName + " by " + user.getUsername());
        }
        else {
            System.out.println("Executing command " + commandName);
        }
        return command;
    }
    public static ControllerHelper getInstance() {
        if (instance == null) {
            instance = new ControllerHelper();
        }
        return instance;
    }
}
