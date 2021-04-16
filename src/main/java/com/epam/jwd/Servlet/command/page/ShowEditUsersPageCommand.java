package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.service.impl.UserService;

import java.util.List;

public enum ShowEditUsersPageCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final String GET_ROLES = "select role from pharmacy.Roles";

    private final ResponseContext CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "admin/editUsers.jsp";
        }

        @Override
        public String getErrorCode() {
            return null;
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

    @Override
    public ResponseContext execute(RequestContext req) {
        List<String> roles = AbstractDAO.getOptions(GET_ROLES);
        UserService service = new UserService();
        List<UserDto> users = service.findAll();
        req.setAttribute("users", users);
        req.setAttribute("roles", roles);
        return CONTEXT;
    }
}
