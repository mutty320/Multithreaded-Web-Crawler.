# Multithreaded-Web-Crawler.
 Web Crawler using Java to count the number of img tags per page .
 
 
The flow of the program is as follows:

Upon running the program the Listener for the Context is invoked initializing the context prams.

Next is the landing page the index.html which displays the form for submitting the Url.

Upon submitting the request, first the listener is invoked to validate the url. if it is invalid or if the content type 

dose not contain html an exception is thrown and the error page is rendered.

If the url is valid the request is forwarded to the doPost which will invoke the thread. and display html with a link to results.

The link will invoke the doGet which will display the desired info for the current user\thread.

The doGet retrieves the info by accessing the global DB context parameter this is a critical section which will be dealt with as follows.

synchronized:

Im order to aviod race conditions or unexpected behavior when the DB is being accessed simultaneously

by multiple threads, the DB "init" function is "synchronized".

(there is no use of static because there is only a single instance of the DB).

The img counter:

The counter is a read and write resource, and as such it can be accessed

at the same time, by the same thread once for updating the counter,

and again for retrieving its value, that can result in getting inaccurate information.

To deal with this issue, synchronized is applied to the get and set methods as well

as the "init" function so setr's wont interfere with getr's and vice versa.

This will work because Java locks are reentrant.




