package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.service.impl.MedicineService;

import java.util.List;
import java.util.Optional;

public enum ShowUpdateMedicinePageCommand implements Command {
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
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getErrorCode() {
            return null;
        }

        @Override
        public boolean isAjax() {
            return false;
        }
    };

    private final ResponseContext ERROR_REQUEST_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
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

        @Override
        public String getErrorCode() {
            return "400";
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            return ERROR_REQUEST_CONTEXT;
        }
        MedicineService service = new MedicineService();
        Optional<MedicineDto> optMedicine = service.findById(id);
        List<String> consistencies = AbstractDAO.getOptions(GET_CONSISTENCIES);
        req.setAttribute("consistencies", consistencies);
        List<String> categories = AbstractDAO.getOptions(GET_CATEGORIES);
        req.setAttribute("categories", categories);
        optMedicine.ifPresent(medicineDto -> req.setAttribute("medicine", medicineDto));
        req.setAttribute("choice", "update");
        return CONTEXT;
    }
}
