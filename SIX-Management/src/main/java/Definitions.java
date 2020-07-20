

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Definitions
 */
public class Definitions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<String> filesListInDir = new ArrayList<String>();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Definitions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Should return appropritate OVAL definitions here ..
		filesListInDir.clear();
		
		 try {
	            //
	            // The path below is the root directory of data to be
	            // compressed.
	            //
	            String path = getServletContext().getRealPath("data");
	            //OutputStream out = response.getOutputStream();
	            File directory = new File("C:\\ovaldi-5.10.1.2");
	            // TODO: get windows definitions only from SixOvalContext....
	    		File resultsZip = new File(System.getProperty("user.dir")+"\\ovaldi-5.10.1.2.zip");
	            resultsZip.createNewFile();
	            String Zdirectory =System.getProperty("user.dir")+"\\ovaldi-5.10.1.2.zip";
	            
	            String[] files = directory.list();

	            //
	            // Checks to see if the directory contains some files.
	            //
	            if (files != null && files.length > 0) {

	                //
	                // Call the zipFiles method for creating a zip stream.
	                //
	            	                this.zipDirectory(directory, Zdirectory);
	                //byte[] zip = zipFiles(directory, files);

	                //
	                // Sends the response back to the user / browser. The
	                // content for zip file type is "application/zip". We
	                // also set the content disposition as attachment for
	                // the browser to show a dialog that will let user 
	                // choose what action will he do to the sent content.
	                //
	                ServletOutputStream sos = response.getOutputStream();
	                response.setContentType("application/zip");
	                response.setHeader("Content-Disposition", "attachment; filename='DATA.ZIP'");
	                
	                File file = new File(Zdirectory);
	                FileInputStream fis;
	        		try  {
	        			 fis = new FileInputStream(file);
	        			System.out.println("Total file size to read (in bytes) : "+ fis.available());
	        			byte[] buf = new byte[1024];
	        			int content=0;
	        			while ((content = fis.read(buf, 0, buf.length)) > 0) {
	        				// convert to char and display it
	        				//System.out.print((char) content);
	        				sos.write(buf, 0, content);
	        				
	        			}
	        			 sos.close();
	 	                fis.close();
	 	               
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}
	                
	                
	                
	                //sos.write(zip);
	                
	                
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	
	 /**
     * This method zips the directory
     * @param dir
     * @param zipDirName
     */
    private void zipDirectory(File dir, String zipDirName) {
        try {
        	
            populateFilesList(dir);
            //now zip files one by one
            //create ZipOutputStream to write to the zip file
           // File file = new File("C:\\ovaldi-5.10.1.2.zip");
           // file.createNewFile();
            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir){
                System.out.println("Zipping "+filePath);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method populates all the files in a directory to a List
     * @param dir
     * @throws IOException
     */
    private void populateFilesList(File dir) throws IOException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else populateFilesList(file);
        }
    }
	
	
	  /**
     * Compress the given directory with all its files.
     */
    private byte[] zipFiles(File directory, String[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];

        for (String fileName : files) {
            FileInputStream fis = new FileInputStream(directory.getPath() + System.getProperty("file.separator") + fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);

            zos.putNextEntry(new ZipEntry(fileName));

            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
            fis.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        return baos.toByteArray();
    }

}
