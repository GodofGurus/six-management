import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.naming.*;

import org.xml.sax.InputSource;



import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;

import jp.go.aist.six.oval.core.SixOvalContext;
import jp.go.aist.six.oval.model.definitions.DefinitionType;
import jp.go.aist.six.util.repository.QueryParams;
import jp.go.aist.six.util.repository.QueryResults;
import jp.go.aist.six.vuln.core.SixVulnContext;
import jp.go.aist.six.vuln.model.scap.cve.ItemType;
/**
* Cron Job.
*
* @author Daynight
* @version 1.0 02/22/2011
*/
public class CronJob {
private final Timer timer = new Timer();
private int minutes = 15; //change to 15

/**
* Web Call method.
*
*/
public void getJobInfo() {
try {
//Properties from web.xml enviornment file
Context initCtx = new InitialContext();
Context envCtx = (Context)initCtx.lookup("java:comp/env");
String ctxminutes = (String) envCtx.lookup("Minutes");
this.minutes = Integer.parseInt(ctxminutes);
startScheduler();
}
catch (Exception e) {
}
}

private SyndFeed getSyndFeedForUrl(String url) throws MalformedURLException, IOException, IllegalArgumentException, FeedException {
	 
    SyndFeed feed = null;        
    InputStream is = null;
 
    try {
 
        URLConnection openConnection = new URL(url).openConnection();            
        is = new URL(url).openConnection().getInputStream();
        if("gzip".equals(openConnection.getContentEncoding())){
            is = new GZIPInputStream(is);
        }            
        InputSource source = new InputSource(is);            
        SyndFeedInput input = new SyndFeedInput();
        feed = input.build(source);
 
    } catch (Exception e){
        System.out.println("Exception occured when building the feed object out of the url");
        e.printStackTrace();
    } finally {
        if( is != null)    is.close();
    }
 
    return feed; 
}


/**
* start schedular.
*
*/
private void startScheduler() {
try {
timer.schedule(new TimerTask() {
public void run() {
scheduleParse();
//timer.cancel();
}


private void scheduleParse() {
	
	System.out.println("HI i am OVAL/CVE update cron job");

	
	
	SyndFeed feed = null;
	try {
		 feed = getSyndFeedForUrl("http://nvd.nist.gov/download/nvd-rss.xml");
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FeedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  
	 
    for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
        String title = entry.getTitle();
        String uri = entry.getUri();
        System.out.println(title.split(" ")[0]);
        System.out.println(uri);
        System.out.println(entry.getDescription().getValue());
       
        QueryParams qp = new QueryParams();
	    qp.set("id",title.split(" ")[0]);
	    QueryResults<ItemType> list =SixVulnContext.Cve.repository().getRepository().findCveItem(qp);
	    if(list.iterateElements().hasNext())
	    {
	    	//update existing
	    	continue;
	    }
	    ItemType cveEntry = new ItemType();
	   cveEntry.setName(title.split(" ")[0]);
	   cveEntry.setDesc(entry.getDescription().getValue());
	    SixVulnContext.Cve.repository().getRepository().saveCveItem(cveEntry);
	
    }
    
    //OVAL FEED 
		SyndFeed Ovalfeed = null;
		try {
			Ovalfeed = getSyndFeedForUrl("http://oval.mitre.org/repository/data/rss");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // List<SyndFeed> myfeed =  feed.getEntries();
        for (SyndEntry entry : (List<SyndEntry>) Ovalfeed.getEntries()) {
            String title = entry.getTitle();
            String uri = entry.getUri();
            System.out.println(title.split(" ")[1]);
            //System.out.println(uri);
            System.out.println(entry.getDescription().getValue());
            
            QueryParams Ovalqp = new QueryParams();
            Ovalqp.set("id",title.split(" ")[1]);
            QueryResults<DefinitionType> Ovallist = SixOvalContext.repository().getRepository().findDefinition(Ovalqp);
            if(Ovallist.iterateElements().hasNext())
            {
            	//update existing
            	continue;
            }
                   DefinitionType Ovalentry = new DefinitionType();
                    Ovalentry.setOvalId(title.split(" ")[1]);
            		SixOvalContext.repository().getRepository().saveDefinition(Ovalentry);
        }
        
    
          }
      }, 0, this.minutes * 60 * 1000);
   }
  catch (Exception e) {
           }


    }



  }