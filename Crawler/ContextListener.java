package com.crawl.ex3;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * the ContextListener is used in order to set 2 global attributes
 * 1. the class "imgCounter" which controls the DB holding the info for each thread.
 * 2. the "maxDepth" parameter and initializes it to 2.
 */
@WebListener
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        ctx.setAttribute("imgCounter", new imgCounter());
        String depth = ctx.getInitParameter("maxDepth");
        ctx.setAttribute(depth, 2);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
