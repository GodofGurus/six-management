

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import jp.go.aist.six.oval.core.SixOvalContext;
import jp.go.aist.six.vuln.core.SixVulnContext;
import jp.go.aist.six.vuln.model.scap.core.CheckReferenceType;
import jp.go.aist.six.vuln.model.scap.vulnerability.VulnerabilityType;

/**
 * Servlet implementation class Search
 */
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	 String query =request.getParameter("query");
	 
	 
	VulnerabilityType vn= SixVulnContext.Nvd.repository().getRepository().findVulnerabilityById(query);
	Collection<CheckReferenceType> ccc = vn.getAssessmentCheck();
   String id = vn.getCveId();
   String DTime = vn.getDiscoveredDatetime();
   String summary  = vn.getSummary();
 //  double TempScore = vn.getCvss().getTemporalMetrics().getScore();
//   double basescore =  vn.getCvss().getEnvironmentalMetrics().getScore();
   Iterator<String>  x = vn.getVulnerableSoftwareList().getProduct().iterator();
   String vunlerablelist = "";
   while(x.hasNext())
   {  
	   vunlerablelist += "</br>" + x.next();
	}

response.setContentType("text/html");  
PrintWriter out = response.getWriter();  

out.println("<title>Search results</title>");
out.println("<body>");
out.println("");
//out.println(SixOvalContext.RepositoryContext.repository().getRepository().countOvalResults());
out.println("</br>");
out.println("<table border='1'><tbody>");
out.println("<tr>");
out.println("<td>");
out.println("CVE ID");
out.println("</td>");
out.println("<td>");
out.println("Discovery Time");
out.println("</td>");
out.println("<td>");
out.println("Summary");
out.println("</td>");	 
out.println("<td>");
out.println("Temporal Score");
out.println("</td>");
out.println("<td>");
out.println("Base Score");
out.println("</td>");
out.println("<td>");
out.println("Vulnerable software");
out.println("</td>");
out.println("</tr>");

out.println("<tr>");
out.println("<td>");
out.println(id);
out.println("</td>");
out.println("<td>");
out.println(DTime);
out.println("</td>");
out.println("<td>");
out.println(summary);
out.println("</td>");	 
out.println("<td>");
//out.println(TempScore);
out.println("</td>");
out.println("<td>");
//out.println(basescore);
out.println("</td>");
out.println("<td>");
out.println(vunlerablelist);
out.println("</td>");
out.println("</tr>");


out.println("</tbody></table></body>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
