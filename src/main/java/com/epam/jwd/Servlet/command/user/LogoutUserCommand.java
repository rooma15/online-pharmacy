package com.epam.jwd.Servlet.command.user;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;

import javax.servlet.http.HttpSession;

public enum LogoutUserCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    private final ResponseContext LOGOUT_CONTEXT = new ResponseContext() {
        @Override
        public boolean isAjax() {
            return false;
        }

        @Override
        public String getPage() {
            return "index.jsp";
        }

        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        String locale = (String)req.getSession().getAttribute("locale");
        req.invalidateSession();
        if(locale != null){
            HttpSession session = req.getSession();
            session.setAttribute("locale", locale);
        }
        return LOGOUT_CONTEXT;
    }
}
