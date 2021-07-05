# Multithreaded-Web-Crawler.
 Web Crawler using Java to count the number of img tags per page .
 
 
The flow of the program is as follows:

Upon running the program the Listener for the Context is invoked initializing the context prams.

Next is the landing page the index.html which displays the form for submitting the Url.

Upon submitting the request, first the listener is invoked to validate the url. if it is invalid or if the content type 

dose not contain html an exception is thrown and the error page is rendered.

If the url is valid the request is forwarded to the doPost which will invoke the thread. and display html with a link to results.

The link will invoke the doGet which will display the desired info for the current user\thread.

The doGet retrieves the info by accessing the global DB context parameter this is a critical section,

synchronized:

A possible issue is, the DB being a global and shared resource being accessed simultaneously by multiple threads,

this can result in a race condition or unexpected behavior.

That is why the DB "init" function is synchronized.

(there is no use of static because there is only a single instance of the DB).

Since the counter is a resource being written into and read from, we might encounter a scenario where the resource is accessed

at the same time by the same thread once for updating the counter,

and again at the same time for retrieving data which will result in returning inaccurate information.

To deal with this issue we use synchronized on the get and set methods as well as the "init" function so set'r wont interfere with the get'r and vice versa.

This will work because Java locks are reentrant.




