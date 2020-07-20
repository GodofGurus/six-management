

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jp.go.aist.six.oval.core.SixOvalContext;
import jp.go.aist.six.oval.core.SixOvalContext.WebClientContext;
import jp.go.aist.six.oval.model.definitions.DefinitionsElement;
import jp.go.aist.six.oval.model.definitions.DefinitionsType;
import jp.go.aist.six.oval.model.definitions.OvalDefinitions;
import jp.go.aist.six.oval.model.definitions.StateType;
import jp.go.aist.six.oval.model.definitions.TestType;
import jp.go.aist.six.oval.model.results.OvalResults;
import jp.go.aist.six.oval.model.results.ResultsType;
import jp.go.aist.six.oval.model.results.SystemType;
import jp.go.aist.six.util.xml.XmlMapper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamSource; 
import javax.xml.transform.stream.StreamResult;

/**
 * Servlet implementation class AssessmentReports
 */
public class AssessmentReports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssessmentReports() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 response.setContentType("text/html");
		  PrintWriter out = response.getWriter();
		  out.println("<title>SIX-Management Assements Reports</title>");
          out.println("<body>");
          out.println("Number of scanned hosts:");
          out.println(SixOvalContext.RepositoryContext.repository().getRepository().countOvalResults());
          out.println("</br>");
          out.println("<table border='1'><tbody>");
          out.println("<tr>");
          out.println("<td>");
          out.println("HostName");
          out.println("</td>");
          out.println("<td>");
          out.println("OS");
          out.println("</td>");
          //out.println("<td>");
          //out.println("Scan Time");
          //out.println("</td>");
          out.println("<td>");
          out.println("Number of vulnerabilities");
          out.println("</td>");
          out.println("<td>");
          out.println("Full Report");
          out.println("</td>");
          out.println("</tr>");
        jp.go.aist.six.util.repository.QueryResults<OvalResults> results;
	    results = WebClientContext.RepositoryContext.repository().getRepository().findOvalResults();
	    List<String> myList = new ArrayList<String>();
     for(Iterator<OvalResults> i = results.iterateElements(); i.hasNext(); ) {
    	
    	 
    	 
    	 int count = 0;   
    	 out.println("<tr>");
           OvalResults item = i.next();

      	 
           ResultsType resultT =  item.getResults();
           
           //resultT.iterator();
           //for(Iterator<OvalResults> i = results.iterateElements(); i.hasNext(); ) {
          System.out.println(i);
          System.out.println("PRODUCT NAME \n");
           System.out.println(item.getGenerator().getProductName());
           System.out.println("SCHEMA VERSION \n");
           System.out.println( item.getGenerator().getSchemaVersion());
        //   System.out.println(item.getOvalDefinitions().getGenerator().getTimestamp());
    //       System.out.println( item.getSchemaLocation() );
           System.out.println("ITEM \n");
           System.out.println( item.toString() );
         //  System.out.println( item.getResults().toString());
      //     System.out.println( item.getOvalDefinitions().toString() );
           System.out.println("ID \n");
          System.out.println( item.getPersistentID().toString()  );
      OvalDefinitions ovld     =     item.getOvalDefinitions();
     Iterator<TestType> itt =  ovld.getTests().iterator();
            DefinitionsType df =    ovld.getDefinitions();
    Collection<StateType>  cstt =   ovld.getStates().getState();
   DefinitionsType ssdsd= new DefinitionsType();
   ResultsType OVR  = item.getResults();
   Iterator<SystemType> ST = OVR.iterator();
 
 
   while(ST.hasNext())
     {
	   SystemType SST = ST.next();
	  
	   System.out.println("HOSTNAME\n");
	   out.println("<td>");
	   out.println(SST.getOvalSystemCharacteristics().getSystemInfo().getPrimaryHostName());
	   out.println("</td>");
	   out.println("<td>");
	   out.println(SST.getOvalSystemCharacteristics().getSystemInfo().getOsName());
	   out.println("</td>");
	   
	  System.out.println( SST.getOvalSystemCharacteristics().getSystemInfo().getPrimaryHostName());
	 // System.out.println("Definitions.digets \n");
	// System.out.println( SST.getDefinitionsDigest());
	  //out.println("<td>");
	
	  //String timeStamp = item.getOvalDefinitions().getGenerator().getTimestamp();
	 // out.println(timeStamp);
	 // out.println("</td>");
	  Iterator<jp.go.aist.six.oval.model.results.TestType> ITT = SST.getTests().iterator();
	  jp.go.aist.six.oval.model.results.DefinitionsType DDFT = SST.getDefinitions();
	    Iterator<jp.go.aist.six.oval.model.results.DefinitionType> ITDDD = 	   DDFT.iterator();
	    
	    while(ITDDD.hasNext())
	    {
	    	jp.go.aist.six.oval.model.results.DefinitionType XITDDD = ITDDD.next();
	    //	System.out.println(XITDDD.getDefinitionClass().value());
	    //	System.out.println("CRITERIA :\n");
	    //	System.out.println(XITDDD.getCriteria());
	   // 	System.out.println("RESULT\n");
	    	if(XITDDD.getResult().value() =="true")
	    		count++;
	       	    	
	    	System.out.println(XITDDD.getResult().value());
	    //	System.out.println("OVAL ID \n");
	    //	System.out.println(XITDDD.getOvalId());
	   // 	System.out.println("OVAL TYPE \n ");
	   // 	System.out.println(XITDDD.ovalGetType().value());
	   /// 	System.out.println("Def id \n");
	    //	System.out.println(XITDDD.getDefinitionId());
	    }
		
     }
   
  
   XmlMapper  xml_mapper = SixOvalContext.repository().getXmlMapper();
   PrintWriter resXml = new PrintWriter(System.getProperty("user.dir")+"\\clientresult" + item.getPersistentID()+".xml");
   
   xml_mapper.marshal(item, resXml);
   TransformerFactory tFactory=TransformerFactory.newInstance();
   
   
   Reader rdr = new InputStreamReader(new FileInputStream("C:\\ovaldi-5.10.1.2\\xml\\results_to_html.xsl"), "ISO-8859-1");  
   StreamSource xslDoc = new StreamSource(rdr);  
   
   Reader rdr2 = new InputStreamReader(new FileInputStream(System.getProperty("user.dir")+"\\clientresult" +item.getPersistentID()+".xml"), "ISO-8859-1");  
   StreamSource xmlDoc = new StreamSource(rdr2);  
  
   
   
   //Source xslDoc=new StreamSource("C:\\ovaldi-5.10.1.2\\xml\\results_to_html.xsl");
  // Source xmlDoc=new StreamSource(System.getProperty("user.dir")+"\\clientresult" +item.getPersistentID()+".xml");
  
   
   String outputFileName=(System.getProperty("user.dir")+"\\tempRes" +item.getPersistentID()+ ".html");
   OutputStream htmlFile=new FileOutputStream(outputFileName);
  
   Transformer trasform=null;
try {
	trasform = tFactory.newTransformer(xslDoc);
} catch (TransformerConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   try {
	trasform.transform(xmlDoc, new StreamResult(htmlFile));
} catch (TransformerException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   
   
   
   
   out.println("<td>");
   out.println(count);
   out.println("</td>");
  out.println("<td>");
  out.println("<a href='http://localhost:8080/SIX-Management/HtmlReports?res="+item.getPersistentID()+"'>HTML Report Click Here</a>"); //'>Complete report</a>" + item.getPersistentID()); 
  //out.println(count);
   out.println("</td>");
	out.println("</tr>");  
 
     }
     out.println("</tbody></table></body>");
 	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
