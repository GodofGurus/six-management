import java.applet.Applet;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.swing.JTextArea;


public class submitResults extends Applet implements ActionListener {
	
    Button jbutton = new Button("Run OVAL Interpreter");
    JTextArea myJtextArea = new JTextArea();

	public submitResults() throws HeadlessException {
		this.setLayout(new FlowLayout());
		this.add(jbutton);
		this.add(myJtextArea);
        jbutton.setLocation(20, 20);
      //  myJtextArea.setLocation(0, 0);
    	jbutton.addActionListener(this);
    	
	}	


	public void actionPerformed(ActionEvent e) {
		final submitResults obj =new submitResults();
		final String command = "C:\\ovaldi-5.10.1.2\\ovaldi.exe";
		String output = "XXX";
	output=	(String) AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                // put the privileged code here, example:
             String res =   obj.executeCommand(command);
                return res; // in our scenario nothing to return
            }
        });
       
		
		myJtextArea.setText(output);
		
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

}
