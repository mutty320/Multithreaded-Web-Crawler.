package com.crawl.ex3;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

/**
 * this thread is instantiated for every request.
 * it holds the getPageLinks function which uses recursion
 * to crawl all the links of the giving Url and count their img elements.
 * it receives in its c'tor: url , user id, the DB, and the depth for the recursion condition.
 * the run function calls the getPageLinks function to preform the crawling,
 * and the end function upon ending to update the server the thread has ended.
 */
public class crawlingThread extends Thread {

    private  String Url;
    private int currentImg, numberOfImg=0;
    private int id;
    private imgCounter counter;
    private static int maxDepth;
    private HashSet<String> links;

    public crawlingThread(imgCounter counte, int num,String url, int depth){
        links = new HashSet<>();
        counter = counte;
        id= num;
        Url= url;
        maxDepth = depth;}

    public void run() {
        System.out.println("Starting Thread for url: "+"["+ Url+"]");
        getPageLinks(Url,0);
        counter.end(id);
    }

    public  void getPageLinks(String url, int depth) {
        if ((!links.contains(url) && (depth < maxDepth))) {
            try {
                links.add(url);
                System.out.println("Begin crawling for current url: "+ " [" + url + "]" +" at depth: " + depth );

                Document document = Jsoup.connect(url).get();
                Elements linksOnPage = document.select("a[href]");

                Elements imageElements = document.select("img");//get <img> elements

                currentImg= imageElements.size();
                System.out.println("number of images found for current url: "+  "["+ url+"]"  +" is: "+currentImg );

                numberOfImg += currentImg /*imageElements.size()*/;
                counter.addImg(id,numberOfImg);

                depth++;

                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("For '" + url + "': " + e.getMessage());
            }

        }
       System.out.println("End crawling with url- "+"["+ url+"]"+ " at depth "+depth);

    }


}


