import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;



/**
 * @author Talya Kalimi
 *
 */

public class FileFactory {
	
	String fileType;

	/**
	 * 
	 */
	public FileFactory() {}

	/**
	 * 
	 * @param type
	 */
	public void setFileType(String type) {
		fileType = type;
	}
	
	public String openUserFile(String path) throws Exception {
		
		String ansString;
		File myFile = null;
		
		myFile = new File(path);
		ansString = FileUtils.readFileToString(myFile);

		return ansString;
	}
}
