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

A possible issue is, the DB being a global and shared resource being accessed simultaneously by multiple threads,

this can result in a race condition or unexpected behavior.

That is why the DB "init" function is synchronized.

(there is no use of static because there is only a single instance of the DB).

The img counter-

Since the counter is a resource being written into and read from, a scenario where the resource is accessed

at the same time, by the same thread once for updating the counter,

and again for retrieving data is possible and can result in returning inaccurate information.

To deal with this issue synchronized is applied to the get and set methods as well as the "init" function so setr's wont interfere with getr's and vice versa.

This will work because Java locks are reentrant.




