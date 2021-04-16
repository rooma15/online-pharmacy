package com.epam.jwd.Servlet.command.page;
import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.service.impl.MedicineService;
import java.util.List;

public enum ShowMainPageCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return false;
    }

    private final ResponseContext MAIN_PAGE_CONTEXT = new ResponseContext() {
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
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        if(req.getAttribute("allItems") == null){
            MedicineService medicineService = new MedicineService();
            List<MedicineDto> allMedicines = medicineService.findAll();
            req.setAttribute("allItems", allMedicines);
        }
        return MAIN_PAGE_CONTEXT;
    }
}
