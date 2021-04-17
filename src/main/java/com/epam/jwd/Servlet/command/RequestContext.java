package com.epam.jwd.Servlet.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;

public interface RequestContext {
     void setAttribute(String name, Object obj);
     Object getAttribute(String name);
     String getParameter(String name);
     void invalidateSession();
     HttpSession getSession();
     String getQueryString();
     Enumeration<String> getParameterNames();
     String[] getParameterValues(String var1);
    Part getPart(String file) throws IOException, ServletException;
}
