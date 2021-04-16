package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.PrescriptionDto;
import com.epam.jwd.Servlet.service.impl.PrescriptionService;

import java.util.List;

public enum ShowDoctorPageCommand implements Command {
    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final ResponseContext SUCCESS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "/doctor/index.jsp";
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
            return null;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        PrescriptionService prescriptionService = new PrescriptionService();
        List<PrescriptionDto> accepted = prescriptionService.findAccepted(true);
        List<PrescriptionDto> notAccepted = prescriptionService.findAccepted(false);
        req.setAttribute("accepted", accepted);
        req.setAttribute("notAccepted", notAccepted);
        return SUCCESS_CONTEXT;
    }
}
