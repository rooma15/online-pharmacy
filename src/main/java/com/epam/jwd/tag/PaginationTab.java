package com.epam.jwd.tag;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.Util.Util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;
import java.util.List;

public class PaginationTab extends TagSupport{

    private int onPage;

    public void setOnPage(int onPage) {
        this.onPage = onPage;
    }

    private List<MedicineDto> items;

    public void setItems(List<MedicineDto> items) {
        this.items = items;
    }


    /**
     * this tag creates pagination tab from elements given as attribute in jsp and insert it in page
     * @return SKIP_BODY constant
     * @throws JspException
     */
    @Override
    public int doStartTag() throws JspException {

        if(items.isEmpty()){
            return SKIP_BODY;
        }

        int pagesCount = items.size() % onPage == 0
                ? items.size() / onPage : items.size() / onPage + 1;
        StringBuilder out = new StringBuilder();
        ServletRequest request = pageContext.getRequest();
        HttpServletRequest req = (HttpServletRequest) request;
        String queryString = req.getQueryString();


        int currentPage = 1;
        if(request.getParameter("page") != null){
            try {
                currentPage = Integer.parseInt((String) request.getParameter("page"));
            } catch (NumberFormatException e) {
                Util.lOGGER.error(e.getMessage());
            }
        }

        for(int i = 0; i < pagesCount; i++) {
            if(queryString.contains("page")){
                queryString = queryString.replaceFirst("&page=[1-9]{1,3}", "");
            }
            out.append("<a href='")
                    .append("/Controller")
                    .append("?")
                    .append(queryString)
                    .append("&page=").append(i + 1)
                    .append("' class='pagination-elems");
                    if(currentPage == i + 1){
                        out.append(" active-tab'>");
                    }
                    else {
                        out.append("'>");
                    }
                    out.append(i + 1)
                    .append("</a> ");
        }
        JspWriter writer = pageContext.getOut();
        try {
            writer.write(out.toString());
        } catch (IOException e) {
            Util.lOGGER.error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
