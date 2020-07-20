

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.aist.six.oval.core.SixOvalContext;
import jp.go.aist.six.oval.model.results.OvalResults;
import jp.go.aist.six.util.xml.XmlMapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class recieveResults1
 */
@WebServlet(name = "recieveResults1", urlPatterns = { "/recieveResults1" })
@MultipartConfig
public class recieveResults1 extends HttpServlet {
	String uploadFolder ="";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public recieveResults1() {
        super();
        // TODO Auto-generated constructor stub
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
	
	@Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		List<FileItem> items = null;
		// Parse the request
		try {
			 items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("GOOD");
		File resultsXML = new File(System.getProperty("user.dir")+"\\clientresult.txt");
        resultsXML.createNewFile();
	
 
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = iter.next();

		    if (item.isFormField()) {
		       ;
		    } else {
		      
		        System.out.println(item.getName());
		        try {
					item.write(resultsXML);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
		
		 XmlMapper  xml_mapper = SixOvalContext.repository().getXmlMapper();
	        OvalResults oval_res= null;
			try {
				oval_res = xml_mapper.unmarshal( new FileInputStream( resultsXML ), OvalResults.class );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		SixOvalContext.RepositoryContext.repository().getRepository().saveOvalResults(oval_res);
       }
	
	
	//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//}
	
	 



}
