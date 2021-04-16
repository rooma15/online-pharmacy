package com.epam.jwd.Servlet.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/index.jsp"})
public class MainPageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/Controller?action=main_page");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

    }
}