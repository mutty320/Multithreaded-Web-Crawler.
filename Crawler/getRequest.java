package com.crawl.ex3;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * this is the main Servlet dealing with the users request.
 */
@WebServlet(name = "getRequest", value = "/getRequest")
public class getRequest extends HttpServlet {

    private int hitCount;

    public void init() {
        // Reset hit counter.
          hitCount = 0;
    }

    /**
     * the doGet is responsible to display the current crawling results and to
     * check if the thread ended and display that with the final result.
     * it is able to get the right info by retrieving the session parameter
     * which holds the users id.
     * using that id makes it possible to get all the info held in the DB.
     * @see imgCounter
     * just in case somone will get here before subbmiting the form we
     * catch that and display a proper message.
     * @param request is the request to see the current results for crawling.
     * @param response is html with the results.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");


        HttpSession session = request.getSession();

        Integer id = (Integer) session.getAttribute("id");

        ServletContext context = getServletContext();
        context.getAttribute("imgCounter");
        imgCounter imgCount = (imgCounter) context.getAttribute("imgCounter");


        PrintWriter out = response.getWriter();

        try {
            String urlSeed = imgCount.getUrl(id);

            int currentCounter = imgCount.getImgCount(id);

            if (imgCount.checkDead(id)) {//when thread is dead
                out.println("<h2> crawling URL :" + urlSeed + "  ended</h2>");
                out.println("<h2>the Final number of images is: " + currentCounter + "</h2>");
            } else {
                out.println("<h3>Still crawling, please reload <a href='/getRequest'>this page</a> later for final results.</h3>");
                out.println("<h2>the current result for crawling URL " + urlSeed + " is:</h2> ");
                out.println(currentCounter);
            }

            out.println("<a href='index.html'>go back to main</a>");

            out.close();

        }
        catch (NullPointerException | InterruptedException e)
            {
                out.println("<h2>Please first submit a URL</h2>");
                out.println("<h3><a href='index.html'>back to main.</a></h3>");

                return;
            }
    }


    /**
     * the doPost gets the url from the form parameter
     * and updates the session to the hitCount variable
     * which will be the users id for the current request
     * then will initialize a new value in the hashMap
     * to represent the current thread.
     * then will instantiate a new thread to start crawling.
     * and will return html response inorder to get the results.
     * then will increment hitCount to be different for the next user or the next request.
     * @param request is the form submitted with the desired url.
     * @param response is html with link to results.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("cuur hit count = "+hitCount);
        String Url= request.getParameter("url");

        HttpSession session = request.getSession();
        session.setAttribute("id", hitCount);

        ServletContext context = getServletContext();
        int depth=(Integer)context.getAttribute("maxdepth");

        context.getAttribute("imgCounter");
        imgCounter imgCount = (imgCounter) context.getAttribute("imgCounter");
        imgCount.init(hitCount,Url);
        crawlingThread thread=new crawlingThread(imgCount,hitCount,Url,depth);
        hitCount++;
        thread.start();
        //PrintWriter out = response.getWriter();
        out.println("<h2> crawling for Url- "+Url+" started:</h2>");
        out.println("<a href='getRequest'>get your results</a>");

        out.close();
        //hitCount++;
    }

}
