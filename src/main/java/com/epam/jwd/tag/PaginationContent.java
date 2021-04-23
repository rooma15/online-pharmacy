package com.epam.jwd.tag;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.Util.Util;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;
import java.util.List;

public class PaginationContent extends TagSupport {
    private int onPage;

    public void setOnPage(int onPage) {
        this.onPage = onPage;
    }


    private List<MedicineDto> items;

    public void setItems(List<MedicineDto> items) {
        this.items = items;
    }


    /**
     * checks if given page is valid
     * @param request servlet request
     * @param response servlet response
     * @param pagesCount all amount of pages
     * @return number of page if everythinf is okay, 0 otherwise
     */
    static int checkPage(ServletRequest request, ServletResponse response, int pagesCount){
        int page = 1;
        HttpServletResponse resp = (HttpServletResponse)response;
        if(request.getParameter("page") != null){
            try {
                page = Integer.parseInt((String) request.getParameter("page"));
            }catch (NumberFormatException e){
                try {
                    ((HttpServletResponse) response).sendError(400);
                    return SKIP_BODY;
                } catch (IOException ex) {
                    Util.lOGGER.error(ex.getMessage());
                }
            }
            if(page > pagesCount){
                try {
                    resp.sendError(404);
                    return SKIP_BODY;
                } catch (IOException e) {
                    Util.lOGGER.error(e.getMessage());
                }
            }
        }
        return page;
    }


    /**
     * this tag divides all content into partition content for every page and writes into
     * request attribute paginated content for given page
     * @return SKIP_BODY constant
     * @throws JspException
     */
    @Override
    public int doStartTag() throws JspException {
        ServletRequest request = pageContext.getRequest();
        ServletResponse response = pageContext.getResponse();

        if(items.isEmpty()) {
            return SKIP_BODY;
        }

        int pagesCount = items.size() % onPage == 0
                ? items.size() / onPage : items.size() / onPage + 1;


        int page = checkPage(request, response,pagesCount);
        if(page == SKIP_BODY){
            return SKIP_BODY;
        }

        List<MedicineDto> medicines;
        if(page == pagesCount){
            medicines = items.subList(page * onPage - onPage, items.size());
        }
        else {
            medicines = items.subList(page * onPage - onPage, page * onPage);
        }
        request.setAttribute("paginatedItems", medicines);
        return SKIP_BODY;
    }
}
