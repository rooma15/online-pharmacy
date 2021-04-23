package com.epam.jwd.Servlet.listener;

import com.epam.jwd.ConnectionPool.ConnectionPool;
import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;
import com.epam.jwd.Servlet.Util.Util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ApplicationListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool connectionPool = DBConnectionPool.getInstance();
        try {
            connectionPool.init();
        } catch (SQLException e) {
            Util.lOGGER.error(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnectionPool.getInstance().destroy();
    }
}
