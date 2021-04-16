package com.epam.jwd.Servlet.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;

public class ApplicationRequestContext implements RequestContext{

    private final HttpServletRequest request;

    private ApplicationRequestContext(HttpServletRequest request){
        this.request = request;
    }

    @Override
    public void setAttribute(String name, Object obj) {
        request.setAttribute(name, obj);
    }

    @Override
    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return request.getParameterNames();
    }

    @Override
    public Part getPart(String file) throws IOException, ServletException {

        return request.getPart(file);
    }

    @Override
    public HttpSession getSession() {
        return request.getSession();
    }

    @Override
    public String getQueryString() {
        return request.getQueryString();
    }

    @Override
    public String getParameter(String name){
        return request.getParameter(name);
    }

    @Override
    public void invalidateSession() {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    static public RequestContext getInstance(HttpServletRequest request){
        return new ApplicationRequestContext(request);
    }
}
