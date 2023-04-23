package com.crawl.ex3;

import java.util.HashMap;

/**
 * this class operates as the DB to associate each thread with
 * their corresponding input.
 * it dose so by using a hash map where the key is the id of the user
 * and the value is the inner class "Info" which holds the url seed
 * the counter for images found in crawling process
 * and a boolean to represent the life of th thread.
 * it also has seter and geter methoeds.
 */
public class imgCounter {
    boolean available =false;
    HashMap<Integer, Info> imgCount = new HashMap<>();
//*****************************************************
    class Info{

    Info(String url){Url= url;};
    String Url;
    boolean isAlive = true;
    int counter=0;
    public void set(int num){counter=num;}
    public int getCount(){return  counter;}
    public void endThread(){isAlive=false;}
    public boolean  isDead(){return !isAlive;}
    public String currUrl(){return Url ;}
    }
//********************************************************

    /**
     * this function adds on to the map any new thread
     * @param id is the id for the new thread
     * @param url the url seed
     */
    public synchronized void init(int id ,String url){
        Info info = new Info(url);

        imgCount.put(id,info);
    }
//********************************************************

    /**
     * this function is called for every recursive call of the
     * crawling function in order to increment the counter for
     * the number of images found.
     * it is synchronized because the counter we are trying
     * to update might be accessed at the same time by the
     * "getImgCount" function.
     * @param id the thread id
     * @param count holds the number of images
     */
    public synchronized void addImg(int id, int count) throws InterruptedException {
        imgCount.get(id).set(count);

    }
//********************************************************

    /**
     * this is a geter function to retrieve the number
     * of images found at every given moment
     * it is synchronized because the number we are trying
     * to retrieve might be updated at the same time by the
     * "addImg" function.
     * @param id the thread id
     * @return the current count
     */
    public synchronized int getImgCount(int id) throws InterruptedException {
        return imgCount.get(id).getCount();
    }
//********************************************************
    /**
     * this function is called when the "run" function
     * is about to end indicating the end of the thread thus
     * updating the boolean indicating so.
     * @param id the thread id
     */
    public void end(int id){
        imgCount.get(id).endThread();
    }
//********************************************************

    /**
     * this function checks the thread status.
      * @param id the thread id
     * @return the boolean representing the status of the thread.
     */
    public boolean checkDead(int id){
       return imgCount.get(id).isDead();
    }
//********************************************************

    /**
     * this function retrieves the url seed for display.
     * @param id the thread id
     * @return the url seed
     */
    public String getUrl(int id){
        return imgCount.get(id).currUrl();
    }
}
//********************************************************

