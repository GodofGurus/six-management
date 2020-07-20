

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.zip.GZIPInputStream;

import javax.servlet.*;
import javax.servlet.http.*;

import org.xml.sax.InputSource;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
/**
* Parser Schedular to run every one hour to parse the file.
*
* @author Daynight
* @version 1.0 02/23/2011
*/

public class Update extends HttpServlet {
/**
* init method - when tomcat server starts the servlet init.
*
* @param config- ServletConfig 
*/
public void init(ServletConfig config) throws ServletException {
super.init(config);
try {
CronJob pp = new CronJob ();
pp.getJobInfo();
}
catch (Exception e) {
}
}

/**
* Get method.
*
* @param request - HttpServletRequest
* @param response - HttpServletResponse
*/
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request, response);
}




/**
* Post method.
*
* @param request - HttpServletRequest
* @param response - HttpServletResponse
*/
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
}
}




