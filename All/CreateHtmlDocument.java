package All;
import java.io.*;

/**
 * @author Talya Kalimi
 *
 */
public class CreateHtmlDocument {
	
	public CreateHtmlDocument () {}
	 public void createHtml(String arr)
	    {
	        try {
	            //define a HTML String Builder
	            StringBuilder htmlStringBuilder=new StringBuilder();
	            
	            //append html header and title
	            htmlStringBuilder.append("<head>");
	            htmlStringBuilder.append("<script src=\"https://cdn.plot.ly/plotly-latest.min.js\"></script>");
	            htmlStringBuilder.append("</head>");
	            //append body
	            htmlStringBuilder.append("<body>");
	            htmlStringBuilder.append("<div id=\"myDiv\"></div>");
	            htmlStringBuilder.append("<script>");
	            htmlStringBuilder.append(arr +
	            		"		var trace = {\r\n" + 
	            		"			x: x,\r\n" + 
	            		"			type: 'histogram',\r\n" + 
	            		//" 			cumulative: {enabled: true},\r\n" +
	            		"		  };\r\n" + 
	            		"		var data = [trace];\r\n" + 
	            		"		Plotly.newPlot('myDiv', data);\r\n" + 
	            		"        </script>\r\n" + 
	            		"        </body>");
	      
	            WriteToFile(htmlStringBuilder.toString(),"punc.html");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    public void WriteToFile(String fileContent, String fileName) throws IOException {
	        String projectPath = System.getProperty("user.dir");
	        String tempFile = projectPath + File.separator+fileName;
	        File file = new File(tempFile);
	        // if file does exists, then delete and create a new file
	        if (file.exists()) {
	            try {
	                File newFileName = new File(projectPath + File.separator+ "backup_"+fileName);
	                file.renameTo(newFileName);
	                file.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        //write to file with OutputStreamWriter
	        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
	        Writer writer=new OutputStreamWriter(outputStream);
	        writer.write(fileContent);
	        writer.close();

	    }

}
