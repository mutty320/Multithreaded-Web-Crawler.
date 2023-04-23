
package com.crawl.ex3;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * we use the following filter to validate the url
 * if it is invalid we send an error page.
 */
@WebFilter(
        urlPatterns = "/getRequest",
        filterName = "Url Filter",
        description = "Filter all URL request")
public  class  UrlFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    public void doFilter(
        ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(req.getMethod().equals("POST")) {

            String UrlSeed = req.getParameter("url");

            try {
                URL url = new URL(UrlSeed);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                if(!(huc.getContentType().contains("html")))
                    throw new IOException();
                int responseCode = huc.getResponseCode();
                RequestDispatcher rd = request.getRequestDispatcher("/getRequest");
                rd.forward(req,res);
            }
            catch (MalformedURLException e) {
                res.sendRedirect("/error-404.html");
            }
            catch (IOException e) {
                res.sendRedirect("/error-404.html");
            }
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/getRequest");
            rd.forward(req,res);
        }

    }

    public void destroy() {
    }

}
