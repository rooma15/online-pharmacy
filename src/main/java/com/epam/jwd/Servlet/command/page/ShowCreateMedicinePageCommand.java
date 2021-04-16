package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import java.util.List;

public enum ShowCreateMedicinePageCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final String GET_CONSISTENCIES = "select consistency from pharmacy.Consistencies";
    private final String GET_CATEGORIES = "select category from pharmacy.Categories";
    
    private final ResponseContext CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "admin/editMedicines.jsp";
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
        List<String> consistencies = AbstractDAO.getOptions(GET_CONSISTENCIES);
        req.setAttribute("consistencies", consistencies);
        List<String> categories = AbstractDAO.getOptions(GET_CATEGORIES);
        req.setAttribute("categories", categories);
        req.setAttribute("choice", "create");
        return CONTEXT;
    }
}
