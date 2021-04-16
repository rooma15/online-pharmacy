package com.epam.jwd.Servlet.filter;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.model.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = {"/doctor/*"})
public class DoctorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null){
            UserDto user = (UserDto) session.getAttribute("user");
            if(user.getRole().equals(UserRole.DOCTOR.name().toLowerCase())){
                filterChain.doFilter(request, response);
            }else {
                response.sendError(403);
            }
        }else {
            response.sendError(403);
        }
    }

    @Override
    public void destroy() {

    }
}
