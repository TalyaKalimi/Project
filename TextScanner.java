
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import Grammar.Token;
import Lexemes.Lexeme;
import Lexemes.Lexeme.Category;
import Singletons.SingletonDataList;
import Singletons.SingletonFieldsList;
import Singletons.SingletonPuncsList;
import Singletons.SingletonTokenized;
import Lexemes.RegexLexeme;

/**
 * @author Talya Kalimi
 *
 */
public class TextScanner {
	
	String m_myFile;
	boolean ignore= false;
	List<String> fileTokenized = new ArrayList<>();
	
	/**
	 * 
	 */
	public TextScanner() {
		
	}
	
	/**
	 * Parameters Constructor
	 * @param file
	 */
	
	public TextScanner(String file) {
		this.m_myFile = file;
	}
	
	
	/**
	 * Scan all tokens and categorized them.
	 * In addition, save the word position in the tokenized list
	 */
	
	public void ScanFile ()
	{
		SingletonTokenized.getInstance().clear();
		Lexeme toAdd = null;
		List<Token> scannedFile = new ArrayList<>();
		
		for(String str:fileTokenized) {
			if(SingletonPuncsList.getHashInstance().containsKey(str)) {
				toAdd = new RegexLexeme(str, Category.Punctuation);
				SingletonTokenized.getInstance().add(toAdd);
				scannedFile.add(new Token(str, Category.Punctuation));
				
			}
			else if(SingletonFieldsList.getHashInstance().containsKey(str)) {
				toAdd = new RegexLexeme(str, Category.Field);
				SingletonTokenized.getInstance().add(toAdd);
				scannedFile.add(new Token(str, Category.Field));
			}
			else {
				scannedFile.add(new Token(str, Category.Data));
				toAdd = new RegexLexeme(str, Category.Data);
				SingletonTokenized.getInstance().add(toAdd);
				SingletonDataList.getInstance().add(toAdd);
				SingletonDataList.getHashInstance().put(str, toAdd);
			}
		}
		
		Hashtable<String, List<Integer>> allWordsPositions = new Hashtable<>();
		//System.out.println();
		//System.out.println("--- TOKENIZED FILE ----");
		int  position=0;
		List<Integer> positionsList = null;
		String lexemeStr = null;
		for (Token lexeme : scannedFile) {
			lexemeStr = lexeme.getString();
			lexeme.setPosition(position);
			if(allWordsPositions.containsKey(lexemeStr)) {
				allWordsPositions.get(lexemeStr).add(position);
			}
			else {
				positionsList = new ArrayList<>();
				positionsList.add(position);
				allWordsPositions.put(lexemeStr, positionsList);
			}
			//System.out.print("["+ lexemeStr + "] ");
			position++;
		}
	}
	
	/**
	 * 
	 * @param ignore
	 */
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
	
	/**
	 * Tokenized the file into List of Tokens.
	 * Tokenized used punctuation list.
	 * insert into fileTokenized
	 */
	public void firstScanFile() {
		
		 String delimiters = "";
		 for(Lexeme lex: SingletonPuncsList.getInstance()) {
			 if(lex.getCategory()!= Category.Ignore)
				 delimiters+=lex.getString();
		 }
		 
		 
		 String nextToken= "";
		 int size = 0;
	        StringTokenizer st3 = new StringTokenizer(m_myFile , delimiters,  true); 
	        while (st3.hasMoreTokens()) {
	        	nextToken = st3.nextToken();
	        	size = fileTokenized.size();
	        	if(nextToken.equals("/") && fileTokenized.get(size-1).equals("<")) {
	        		nextToken = fileTokenized.get(size-1)+nextToken;
	            	fileTokenized.remove(size-1);
	        	}
	        	fileTokenized.add(nextToken);
	        }
		
	}
	
	/**
	 * 
	 * @return fileTokenized list
	 */
	public List<String> getTokenizedFile() {
		return this.fileTokenized;
	}
	
	
	/**
	 * Check if string is a number
	 * @param strNum
	 * @return boolean
	 */
	/*private boolean isNumeric(String strNum) {
	    try {
	        @SuppressWarnings("unused")
			double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}*/
	/*
	private void firstTokenizeFile() {
		
		String fileToString = m_myFile;
	
		String testString = m_myFile;
		for (Lexeme puncLexeme : SingletonPuncsList.getInstance()) {
			if(puncLexeme.getCategory()==Lexeme.Category.Ignore && (!isNumeric((puncLexeme.getString()))))
				testString = testString.replace(puncLexeme.getString().charAt(0),'~');
		}
		
		Lexeme.Category dataCategory =Lexeme.Category.Data;
		for (Lexeme puncLexeme : SingletonPuncsList.getInstance()) {
			if(!ignore)
				fileToString = fileToString.replace(puncLexeme.getString().charAt(0),'~');
			else if(ignore && !isNumeric(puncLexeme.getString()))
				fileToString = fileToString.replace(puncLexeme.getString().charAt(0),'~');
		}

		String token[] = fileToString.split("~");
		
		for (int i=0; i<token.length; i++) {
			if (!("".equals(token[i]))) {
				Lexeme toAdd = new RegexLexeme(token[i],dataCategory);
				SingletonTokenized.getInstance().add(toAdd);
			}
		}
		
		/*String fileToString = "";
		String subStr = "";
		String data = "";
		Lexeme existLexeme = null;
		Lexeme.Category dataCategory =Lexeme.Category.Data;
		
		try {
			fileToString = FileUtils.readFileToString(m_myFile);
			//fileToString = fileToString.replace("\n", "").replace("\r", "").replaceAll(" ", "");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int fileLen = fileToString.length();
		int lexemeIndex = 0;
		int nextPuncIndex = 0;
		int i = lexemeIndex;
		boolean foundPunc = false;
		
		while (i<fileLen)
		{
			for (int j = i+1; j <= fileLen; j++) {
				subStr = fileToString.substring(i,j);
				if(m_LexemesHash.containsKey(subStr))
				{
					existLexeme = m_LexemesHash.get(subStr);
					nextPuncIndex = j;
					foundPunc = true;
				} //found next index to search for punctuation
				
			}
			if(foundPunc)
			{
				if(i!=lexemeIndex)
				{
					data = fileToString.substring(lexemeIndex,i);
					Lexeme toAdd = new RegexLexeme(data,dataCategory);
					SingletonTokenized.getInstance().add(toAdd);
					SingletonDataList.getInstance().add(toAdd);
				}
				
				if(existLexeme!=null && 
						existLexeme.getCategory()!= Lexeme.Category.Ignore)
				{
					SingletonTokenized.getInstance().add(existLexeme);
				}
				
				lexemeIndex = nextPuncIndex;
				i = lexemeIndex;
				foundPunc = false;
			}
			else
			{
				i++;
			}
			
			
		
			
		}

		
	}*/
	
	
	// NOT USED
	/*public List<Token> tokenizeFile() {
		setIgnore(true);
		SingletonTokenized.getInstance().clear();
		ScanFile();
		Hashtable<String, Integer> tokenizedHash = new Hashtable<>();

		for (Map.Entry<String, Lexeme> entry : SingletonFieldsList.getHashInstance().entrySet()) {
			System.out.println("field:: " +entry.getKey());
		}
	
		for(Lexeme lex: SingletonTokenized.getInstance()) {
			if(SingletonFieldsList.getHashInstance().containsKey(lex.getString()))
				SingletonTokenized.getInstance().remove(lex);
		}
	
		
		String fileToString = m_myFile;
		 List<Token> mylist = new ArrayList<Token>();
		 int index =0;
		 String str;
		 Token token;
		 for(Lexeme punc: SingletonPuncsList.getInstance()) {
			 int jump=0;
			 str=punc.getString();
			 if(tokenizedHash.containsKey(str))
				 jump = tokenizedHash.get(str);
			 index=fileToString.indexOf(str,jump+1);
			 while(!isNumeric(str) && (punc.getCategory()!= Category.Ignore) && index > -1) {
				 token = new Token(str, index, Category.Punctuation);
				 mylist.add(token);
				 tokenizedHash.put(str, index);
				 index=fileToString.indexOf(str, index+str.length());
			 }
		 }
		 
		 for(Lexeme field: SingletonFieldsList.getInstance()) {
			 str=field.getString();
			 index=fileToString.indexOf(str);
			 while(index > -1) {
				 token = new Token(str, index, Category.Field);
				 mylist.add(token);
				 index=fileToString.indexOf(str, index+str.length());
			 }
		 }
		 
		 for(Lexeme data: SingletonTokenized.getInstance()) {
			 int jump=0;
			 str=data.getString();
			 if(tokenizedHash.containsKey(str))
				 jump = tokenizedHash.get(str);
			 index=fileToString.indexOf(str, jump+1);
			 if(index > -1) {
				 token = new Token(str, index, Category.Data);
				 mylist.add(token);
				 tokenizedHash.put(str, index);
				 index=fileToString.indexOf(str, index+str.length());
			 }
		 }
		 
		  if (!mylist.isEmpty()) {
			    Collections.sort(mylist, new Comparator<Token>() {
			        @Override
			        public int compare(Token c1, Token c2) {
			            return c1.getPosition()- c2.getPosition();
			        }
			       });
			   }
		  System.out.println();
		  /*for(Token t: mylist) {
			  System.out.println("Pos:"+ t.getPosition()+" "+t.getToken()+ "\t\t"+ t.getCategory());
			  
		  }
		  return mylist;
	  }
	*/
	
}
