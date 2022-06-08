package project.controller;

import project.commands.CommandMissing;
import project.commands.ECommands;
import project.commands.ICommand;

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
        System.out.println("Executing command "+ commandName);
        return command;
    }
    public static ControllerHelper getInstance() {
        if (instance == null) {
            instance = new ControllerHelper();
        }
        return instance;
    }
}
