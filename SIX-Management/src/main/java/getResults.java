

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.aist.six.oval.core.SixOvalContext;
import jp.go.aist.six.oval.model.results.OvalResults;
import jp.go.aist.six.util.xml.XmlMapper;

/**
 * Servlet implementation class getResults
 */
public class getResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getResults() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null; 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ServletContext sc = this.getServletContext();
        try
            {
            String fileName = "uma2.txt";
            String path = sc.getRealPath(File.separator)+fileName;
        	//String path = "C:\\test.txt";
            File yourFile = new File(path);
            FileOutputStream toFile = new FileOutputStream( yourFile );
            DataInputStream fromClient = new DataInputStream( request.getInputStream() );

            StringBuffer inputLine = new StringBuffer();
            String tmp; 
            while ((tmp = fromClient.readLine()) != null) {
                inputLine.append(tmp);
             //   System.out.println(tmp);
            }
            //use inputLine.toString(); here it would have whole source
            fromClient.close();
            
            
           /* byte[] buff = new byte[1024];
            int cnt = 0;
                while( (cnt = fromClient.read( buff )) > -1 ) {
                toFile.write( buff, 0, cnt );
            }*/
            
            XmlMapper  xml_mapper = SixOvalContext.repository().getXmlMapper();
         //   String  local_src = "C:\\ovaldi-5.10.1.2\\results.xml";
         //   File  src_file = new File( local_src );
            OvalResults oval_res= null;
    		try {
    			oval_res = xml_mapper.unmarshal(new FileInputStream(inputLine.toString() ), OvalResults.class );
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		SixOvalContext.RepositoryContext.repository().getRepository().saveOvalResults(oval_res);
            
            
            toFile.flush();
            toFile.close();
         
            fromClient.close();
           
        }
        catch(Exception e)
            {
            e.printStackTrace();
        }
		
		
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
