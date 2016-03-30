/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Class-Filter for user role challenge
 */

@WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class RoleFilter implements Filter {
    private String indexPath,mainPath;


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = ((HttpServletRequest) request).getSession(true);
        if(session.isNew()){
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        } else {
            if (httpRequest.getSession().getId() == null) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
            }
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter("INDEX_PATH");
    }
    public void destroy() {
    }
}
