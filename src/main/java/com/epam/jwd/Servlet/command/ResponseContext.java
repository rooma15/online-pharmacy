package com.epam.jwd.Servlet.command;

public interface ResponseContext {
    String getPage();
    boolean isRedirect();
    boolean isAjax();
    String getErrorCode();
}
