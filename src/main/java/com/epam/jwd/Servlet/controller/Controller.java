package com.epam.jwd.Servlet.controller;


import com.epam.jwd.Servlet.command.AjaxResponseContext;
import com.epam.jwd.Servlet.command.ApplicationRequestContext;
import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.google.gson.Gson;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {

    private final String COMMAND_PARAMETER_NAME = "action";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String commandName = req.getParameter(COMMAND_PARAMETER_NAME);
        if(commandName == null) {
            resp.sendError(400);
        } else {
            final Command businessCommand = Command.of(commandName);
            final ResponseContext result = businessCommand.execute(ApplicationRequestContext.getInstance(req));
            if(result.getErrorCode() != null) {
                resp.sendError(Integer.parseInt(result.getErrorCode()));
            }
            HttpSession session = req.getSession();
            if(result.isAjax()) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter writer = resp.getWriter();
                writer.write(((AjaxResponseContext) result).getResponseText());
            } else if(!result.isAjax() && result.getErrorCode() == null) {
                if(result.isRedirect()) {
                    resp.sendRedirect(result.getPage());
                } else {
                    final RequestDispatcher dispatcher = req.getRequestDispatcher(result.getPage());
                    dispatcher.forward(req, resp);
                }
            }
        }
    }
}


