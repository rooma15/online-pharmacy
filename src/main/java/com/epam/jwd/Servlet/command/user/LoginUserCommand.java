package com.epam.jwd.Servlet.command.user;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.service.impl.CartService;
import com.epam.jwd.Servlet.service.impl.UserService;
import com.epam.jwd.Util;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.ResourceBundle;

public enum LoginUserCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    private final ResponseContext LOGIN_SUCCESS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "/Controller?action=main_page";
        }
        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public boolean isAjax() {
            return false;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private final ResponseContext LOGIN_ERROR_CONTEXT = new ResponseContext() {
        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public boolean isAjax() {
            return false;
        }

        @Override
        public String getPage() {
            return "login.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };


    @Override
    public ResponseContext execute(RequestContext req) {
        ResourceBundle locale = Util.getLocaleBundle((String)req.getSession().getAttribute("locale"));
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if("".equals(login) || "".equals(password) || login == null || password == null){
            req.setAttribute("error", locale.getString("regError.notAllFields"));
            return LOGIN_ERROR_CONTEXT;
        }

        login = StringEscapeUtils.escapeHtml4(login);
        password = StringEscapeUtils.escapeHtml4(password);

        UserService service = new UserService();
        Optional<UserDto> user = service.login(login, password);
        if(user.isPresent()){
            HttpSession session = req.getSession();
            session.setAttribute("user", user.get());
            CartService cartService = new CartService(req);
            cartService.synchronizeCart();
            return LOGIN_SUCCESS_CONTEXT;
        }else {
            req.setAttribute("error", locale.getString("login.noUser"));
            return LOGIN_ERROR_CONTEXT;
        }
    }
}
