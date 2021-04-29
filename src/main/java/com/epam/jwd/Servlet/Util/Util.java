package com.epam.jwd.Servlet.Util;

import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;
import com.epam.jwd.Servlet.command.ResponseContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Util {
    public static final Logger lOGGER = LogManager.getLogger();


    public static final ResponseContext ERROR_REQUEST_CONTEXT = new ResponseContext() {
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

    public static final ResponseContext NOT_FOUND_REQUEST_CONTEXT = new ResponseContext() {
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
            return "404";
        }
    };

    public static final ResponseContext FORBIDDEN_REQUEST_CONTEXT = new ResponseContext() {
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
            return "403";
        }
    };

    public static ResourceBundle getLocaleBundle(String loc) {
        String[] localeParts = loc.split("_");
        String language = localeParts[0];
        String country = localeParts[1];
        Locale locale = new Locale(language, country);
        return ResourceBundle.getBundle("pagecontent", locale);
    }
}
