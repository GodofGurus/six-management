import java.applet.Applet;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;







import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JTextArea;


public class RunOval extends Applet implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String Boundary = "--7d021a37605f0";
	Button jbutton = new Button("Start");
    JTextArea myJtextArea = new JTextArea();
    
	public RunOval() throws HeadlessException {
		this.setLayout(new FlowLayout());
		this.add(jbutton);
		this.add(myJtextArea);
    	jbutton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
 
		final RunOval obj =new RunOval();
		final String command = "C:\\ovaldi-5.10.1.2\\ovaldi.exe -m -o C:\\ovaldi-5.10.1.2\\definitions.xml";
		String output = "XXX";

		this.downloadDefintions();
		
	output=	(String) AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
            	
                
            	obj.executeCommand("cmd.exe /c  cd C:\\ovaldi-5.10.1.2");
            	//obj.executeCommand("cd C:\ovaldi-5.10.1.2\");
             String res =   obj.executeCommand(command);
                return res; // in our scenario nothing to return
            }
        });
       
		myJtextArea.setText(output);
		int x = 120;
		for(int i =0; i<5000;i++)
		{
		 x = x*120 +1;
		}
		 myJtextArea.setText("Please Wait .... \n  uploading results to server..");
		
		
		    String charset = "UTF-8";
	        File uploadFile1 = new File("C:/ovaldi-5.10.1.2/results.xml");
	        String requestURL = "http://localhost:8080/SIX-Management/recieveResults1";
	   	 
	        try {
	            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
	            multipart.addFilePart("fileUpload", uploadFile1);
	            List<String> response = multipart.finish();
	             
	            System.out.println("SERVER REPLIED:");
	             
	            for (String line : response) {
	                System.out.println(line);
	            }
	        } catch (IOException ex) {
	            System.err.println(ex);
	        }
		
	}
	
	private void downloadOvalDefintions()
	{
		
	}
	
	private void downloadDefintions()
	{	        		

	/*
		final Path path = Paths.get("C:\\ovaldi-5.10.1.2");
		Boolean output = (boolean) AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
            	
            	if (Files.exists( path)) {
           		 downloadOvalDefintions();
    			 return false;
            	}
                return true;
            }
        });
		
		if(!output)
		{return;}
 
		 		
      		  //  String requestOVAL = "http://localhost:8080/SIX-Management/Definitions";
		 AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
            	
      		  URL defURL = null;	
				 try {
				   defURL = new URL ("http://localhost:8080/SIX-Management/Definitions");
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 URLConnection uc= null;
				try {
					uc = defURL.openConnection();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 uc.setDoOutput(true);
				 uc.setDoInput(true);
				 uc.setUseCaches(false);
				 uc.setRequestProperty("Content-Type", 
						   "application/zip");//application/zip
		
		try {
			uc.connect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
     	
 		InputStream in = null;
 		try {
 			in = uc.getInputStream();
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
 		FileOutputStream out =null;
 		try {
 			 out = new FileOutputStream(System.getProperty("user.dir")+"\\ovaldi-5.10.1.2.zip");
 		} catch (FileNotFoundException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
 		
 		int data;
 		try
 		{
 		 while ((data = in.read()) != -1) {
             out.write(data);
         }
 		}
 		catch(IOException e1)
 		{
 		e1.printStackTrace() ;
 		}
 	/*	byte[] b = new byte[1024];
         int count;
         try {
 			while ((count = in.read(b)) > 0) {
 			    out.write(b, 0, count);
 			}
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
         try {
 			out.flush();
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		} try {
 			out.close();
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		} try {
 			in.close();
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		} */
/*
     	unzipFile(System.getProperty("user.dir")+"\\ovaldi-5.10.1.2.zip");
   
     
 		myJtextArea.setText("done downloading definitions");
		return null;

            	
            	
            }
        });
      	            	
		 
		 return;*/
}            	

	
	private void sleep(int i) {
		// TODO Auto-generated method stub
		
	}

	private String executeCommand(String command) {
		 
		StringBuffer output = new StringBuffer();
 //
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));
 
                        String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output.toString();
 
	}
	
	private void unzipFile(String filePath){
        
        FileInputStream fis = null;
        ZipInputStream zipIs = null;
        ZipEntry zEntry = null;
        try {
            fis = new FileInputStream(filePath);
            zipIs = new ZipInputStream(new BufferedInputStream(fis));
            while((zEntry = zipIs.getNextEntry()) != null){
                try{
                    byte[] tmp = new byte[4*1024];
                    FileOutputStream fos = null;
                    String opFilePath = "C:/ovaldi-5.10.1.2/"+zEntry.getName();
                    System.out.println("Extracting file to "+opFilePath);
                    fos = new FileOutputStream(opFilePath);
                    int size = 0;
                    while((size = zipIs.read(tmp)) != -1){
                        fos.write(tmp, 0 , size);
                    }
                    fos.flush();
                    fos.close();
                } catch(Exception ex){
                     
                }
            }
            zipIs.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
