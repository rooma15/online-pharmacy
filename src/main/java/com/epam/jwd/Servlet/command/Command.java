package com.epam.jwd.Servlet.command;

public interface Command {
    ResponseContext execute(RequestContext req);
    static Command of(String name){
        return CommandManager.of(name);
    }
    boolean isUnregisteredClientForbidden();
}
