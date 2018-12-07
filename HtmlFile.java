import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Hashtable;

import Lexemes.Lexeme;
import Singletons.SingletonTokenized;

public class HtmlFile {
	
    
	Hashtable <String,Lexeme> m_LexemesHash = new Hashtable<String,Lexeme>();
	
	public HtmlFile() {
		// TODO Auto-generated constructor stub
	}
	
	 public void createHtml(String fileToString)
	    {
	        try {
	            //define a HTML String Builder
	            StringBuilder htmlStringBuilder=new StringBuilder();
	      
	            String red = "<font color=\"red\">";
	            String blue = "<font color=\"blue\">";
	            String green = "<font color=\"green\">";
	            String yellow = "<font color=\"yellow\">";
	            String endColor = "</font>";
	            
	            //append html header and title
	            //htmlStringBuilder.append("<<embed src=\"C:\\Users\\Talya\\Desktop\\Project\\My Project.pdf\" width=\"800px\" height=\"2100px\" />");
	            htmlStringBuilder.append("<head>");
	            htmlStringBuilder.append("<body>");
	            //htmlStringBuilder.append("Input : \r\n" + fileToString + "\r\n"+ "Output : \r\n" );
	            
	            //<embed src="path_of_your_pdf/your_pdf_file.pdf" type="application/pdf"   height="700px" width="500">
	            
	            for (Lexeme lexeme : SingletonTokenized.getInstance()) {
	            	
	            	switch (lexeme.getCategory()) {
	            	
					case Punctuation:
						htmlStringBuilder.append(red+lexeme.getString()+endColor+ " ");
						break;
					case Field:
						htmlStringBuilder.append(blue+lexeme.getString()+endColor+ " ");
						break;
						
					case Data:
						htmlStringBuilder.append(green+lexeme.getString()+endColor+ " ");
						break;
					
					case Ignore:
						htmlStringBuilder.append(yellow+lexeme.getString()+endColor+ " ");
						break;
						
					default:
						htmlStringBuilder.append(lexeme.getString()+ " ");
						break;
					}
	  
	    		}
	            
	            
	            /*htmlStringBuilder.append(arr +
	            		"		var trace = {\r\n" + 
	            		"			x: x,\r\n" + 
	            		"			type: 'histogram',\r\n" + 
	            		//" 			cumulative: {enabled: true},\r\n" +
	            		"		  };\r\n" + 
	            		"		var data = [trace];\r\n" + 
	            		"		Plotly.newPlot('myDiv', data);\r\n" + 
	            		"        </script>\r\n" + 
	            		"        </body>");*/
	            
	            
	            htmlStringBuilder.append("</body>");
	            htmlStringBuilder.append("</head>");
	      
	            WriteToFile(htmlStringBuilder.toString(),"HtmlFile.html");
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
