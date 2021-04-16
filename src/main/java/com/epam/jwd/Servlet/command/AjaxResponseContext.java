package com.epam.jwd.Servlet.command;

public class AjaxResponseContext implements ResponseContext{

    public AjaxResponseContext(String message) {
        this.message = message;
    }

    private final String message;

    @Override
    public String getErrorCode() {
        return null;
    }

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
        return true;
    }

    public String getResponseText(){
        return message;
    }

}
