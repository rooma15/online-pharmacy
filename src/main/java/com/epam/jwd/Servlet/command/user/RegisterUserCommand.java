package com.epam.jwd.Servlet.command.user;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.model.UserRole;
import com.epam.jwd.Servlet.service.impl.UserService;
import com.epam.jwd.Util;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public enum RegisterUserCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    private final ResponseContext SUCCESS_REGISTER_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "index.jsp";
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

    private final ResponseContext ERROR_ADMIN_REGISTER_CONTEXT = new ResponseContext() {
        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public String getPage() {
            return "/Controller?action=admin_edit_user_page";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public boolean isAjax() {
            return false;
        }
    };

    private final ResponseContext ERROR_CLIENT_REGISTER_CONTEXT = new ResponseContext() {
        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public String getPage() {
            return "register.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public boolean isAjax() {
            return false;
        }
    };


    private ResponseContext getErrorResponse(RequestContext req){
        if(req.getQueryString().contains("admin_register_user")){
            return ERROR_ADMIN_REGISTER_CONTEXT;
        }else {
            return ERROR_CLIENT_REGISTER_CONTEXT;
        }
    }

    @Override
    public ResponseContext execute(RequestContext req) {
        String loc = (String)req.getSession().getAttribute("locale");
        ResourceBundle locale;
        if(loc != null){
            locale = Util.getLocaleBundle((String)req.getSession().getAttribute("locale"));
        }else {
            locale = Util.getLocaleBundle("ru_RU");
        }
        List<String> errors = new ArrayList<>();
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        if(login == null || name == null || password == null ||
        "".equals(login) || "".equals(password)|| "".equals(name)){
            errors.add(locale.getString("regError.notAllFields"));
            req.setAttribute("errors", errors);
            return getErrorResponse(req);
        }


        if(password.length() < 8){
            errors.add(locale.getString("regError.passwordLength"));
            req.setAttribute("errors", errors);
        }

        name = StringEscapeUtils.escapeHtml4(name);
        login = StringEscapeUtils.escapeHtml4(login);
        password = StringEscapeUtils.escapeHtml4(password);

        if(!name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
            errors.add(locale.getString("regError.wrongName"));
        }

        if(!login.matches("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$")){
            errors.add(locale.getString("regError.wrongLogin"));
        }

        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")){
            errors.add(locale.getString("regError.wrongPassword"));
        }

        if(!errors.isEmpty()){
            req.setAttribute("errors", errors);
            return getErrorResponse(req);
        }


        UserRole role;
        if(req.getParameter("role") != null){
            role = UserRole.valueOf(req.getParameter("role").toUpperCase());
        }else{
            role = UserRole.CLIENT;
        }

        UserService service = new UserService();
        Optional<User> user = service.findByLogin(login);
        if(user.isPresent()){
            errors.add(locale.getString("regError.userExists"));
            req.setAttribute("errors", errors);
            return getErrorResponse(req);
        }
        password = BCrypt.hashpw(password, BCrypt.gensalt(10));
        UserDto newUser = new UserDto(login, password, name, role);
        boolean status = service.create(newUser);
        if(status){
            if(!req.getQueryString().contains("admin_register_user")){
                HttpSession session = req.getSession();
                session.setAttribute("user", newUser);
            }
            return SUCCESS_REGISTER_CONTEXT;
        }else{
            return getErrorResponse(req);
        }
    }
}
