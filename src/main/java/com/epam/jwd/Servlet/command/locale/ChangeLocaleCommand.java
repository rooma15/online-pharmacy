package com.epam.jwd.Servlet.command.locale;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.command.page.ShowMainPageCommand;

import javax.servlet.http.HttpSession;
import java.util.Locale;

public enum ChangeLocaleCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        String locale = req.getParameter("locale");
        HttpSession session = req.getSession();
        session.setAttribute("locale", locale);
        return ShowMainPageCommand.INSTANCE.execute(req);
    }
}
