package Grammar;


import java.util.Hashtable;
import All.SuffixArray;
import Lexemes.Lexeme;
import Lexemes.Lexeme.Category;
import Singletons.SingletonFieldsList;
import Singletons.SingletonTokenized;
import Lexemes.RegexLexeme;

/**
 * 
 * @author Talya Kalimi
 *
 */

public class FieldsLearner {

	StringBuilder fileStr;
	SuffixArray sa;
	Hashtable <String,Integer> myHash = new Hashtable<String,Integer>();
	
	/**
	 * Parameters Constructor
	 * @param str - File as string
	 */
	public FieldsLearner(String str) {
		this.fileStr = new StringBuilder(str);
	}

	/**
	 * Learn fields process
	 */
	public void LeranFields() {
	
		StringBuilder fileStrBuilder = new StringBuilder();
		/*for(Lexeme lex: SingletonPuncsList.getInstance()) {
				//fileStr = fileStr.replace(lex.getString(), "");
			fileStr.replace(0, fileStr.length()-1, (fileStr.toString()).replace(lex.getString(), ""));
		}*/
		/*StringBuilder bStringBuilder = new StringBuilder(fileStr);
		sa = new SuffixArray(bStringBuilder.toString());
	    System.out.printf("LRS(s) of %s is/are: %s\n", bStringBuilder.toString() , sa.lrs());*/
	    
		int value = 0;
		for(Lexeme lex: SingletonTokenized.getInstance()) {
			
			if(lex.getCategory() != Category.Punctuation && !lex.getString().matches(".*\\d+.*") && !lex.getString().contains("@")) {
				fileStrBuilder.append(" "+lex.getString());
				/*if (myHash.containsKey(lex.getString())) {
					value = myHash.get(lex.getString());
					myHash.put(lex.getString(), value+1);
				}
				else {
					myHash.put(lex.getString(), 1);
				}*/
			}
		}
		/*for (String key : myHash.keySet()) {
			if(myHash.get(key) > 2) {
				SingletonFieldsList.getInstance().add(new RegexLexeme(key, Lexeme.Category.Field));
			}
		    System.out.println(key + ":" + myHash.get(key));
		}*/
		
		
		System.out.println();

		boolean found = true;
		
		while (found) {
			found = false;
			sa = new SuffixArray(fileStrBuilder.toString());
		    //System.out.printf("LRS(s) of %s is/are: %s\n", temp , sa.lrs());
		    for(String treeField: sa.lrs()) {
		    	//treeField = treeField.replaceAll(" ", "");
		    	String[] words = treeField.split("\\s+");	// split words with " " between them

		    	for(String word:words) {
		    		if(word.length() > 1) {
		    			found = true;
		    			if(!myHash.containsKey(word)) {
			    			fileStrBuilder.replace(0, fileStrBuilder.length()-1, (fileStrBuilder.toString()).replace(word, ""));
			    			RegexLexeme field = new RegexLexeme(word, Lexeme.Category.Field);
			    			SingletonFieldsList.getInstance().add(field);
			    			myHash.put(word, 1);
		    			}
		    		}
		    		else {
		    			//temp = temp.replace(word, "");
		    			if(word.length() == 1 && !Character.isLetter(word.charAt(0)))
		    				fileStrBuilder.replace(0, fileStrBuilder.length()-1, (fileStrBuilder.toString()).replace(word, ""));
		    		}
		    	}
		    	
		    }
		    //System.out.println(temp);
		
		}
		/*for (Lexeme lexeme : SingletonFieldsList.getInstance()) {
			System.out.print(lexeme.getString() + ", ");
		}*/
		/*boolean contain = true;
		String temp= "";
		while(contain) {
			boolean found = false;
			*//*for(Lexeme lex: SingletonPuncsList.getInstance()) {
					fileStr = fileStr.toString().replace(lex.getString(), " ");
			}*/
			
		/*for(Lexeme lex: SingletonFieldsList.getInstance()) {
			System.out.println(lex.getString());
			}*/
			myHash.clear();
			int maxValue = -1;
			for(Lexeme lex: SingletonTokenized.getInstance()) {
				for(Lexeme field: SingletonFieldsList.getInstance()) {
					if(lex.getString().indexOf(field.getString()) > -1) {
						if (myHash.containsKey(field.getString())) {
							value = myHash.get(field.getString());
							myHash.put(field.getString(), value+1);
							if(value+1 > maxValue)
							{
								maxValue = value+1;
							}
						}
						else {
							myHash.put(field.getString(), 1);
							//System.out.println("map : " +field.getString());
						}
					}
				}
			}
			
	
			/*Iterator<Lexeme> it = SingletonFieldsList.getInstance().iterator();
			while (it.hasNext()) {
			    Lexeme lex = it.next();
			    if (myHash.containsKey(lex.getString())){
			    	if(myHash.get(lex.getString()) < maxValue-3)
			    		it.remove();
			    }
			}*/
			/*for (String key : myHash.keySet()) {
			    System.out.println(key + ":" + myHash.get(key));
			}*/
			
			SingletonFieldsList.getInstance().clear();
			
			for (String key : myHash.keySet()) {
				if(!key.matches(".*\\d+.*") && myHash.get(key) >= maxValue-3)
				{
					Lexeme field = new RegexLexeme(key, Lexeme.Category.Field);
					SingletonFieldsList.getInstance().add(field);
					SingletonFieldsList.getHashInstance().put(field.getString(), field);
				}
			}
			
			for(Lexeme lex: SingletonTokenized.getInstance()) {
				if(SingletonFieldsList.getHashInstance().containsKey(lex.getString()))
					lex.setCategory(Category.Field);
			}
	
			/*for(Lexeme lex: SingletonFieldsList.getInstance()) {
				System.out.println(lex.getString());
		}*/
			/*for(Lexeme field: SingletonFieldsList.getInstance()) {
				if(myHash.get(field.getString()) < maxValue-3) {
					
				}
			}*/
					/*temp = fileStr.replaceFirst(lex.getString(), strarr[j]);
					if(temp.compareTo(fileStr)==0)
						found = found | false;
					else {
						found = true;
						fileStr = temp;
					}
					
					j = (j+1) % strarr.length;
			}
			contain = found;
	}*/
			
			
			//System.out.println(fileStr);
		
	
		/*for (int i=0; i< bStringBuilder.length()-1; i++) {
			for(Lexeme lex: SingletonPuncsList.getInstance()) {
				if (bStringBuilder.charAt(i) == lex.getString().charAt(0)) {
					bStringBuilder.setCharAt(i, strarr[ThreadLocalRandom.current().nextInt(0, strarr.length)]);
					j = (j+1) % strarr.length;
				}
			}
		}*/
	/*	System.out.println("-----\n"+bStringBuilder);
		
		boolean stop = false;
		int co =0;
		while (co < 2) {
			sa = new SuffixArray(bStringBuilder.toString());
		    System.out.printf("LRS(s) of %s is/are: %s\n", bStringBuilder.toString() , sa.lrs());
		    for(String field:sa.lrs())
		    {
		    	field = field.replaceAll(" ", "");
		    	System.out.printf("LRS(s) of %s is/are: %s\n", bStringBuilder.toString() , sa.lrs());
		    	fileStr = bStringBuilder.toString().replaceAll(field, "");
		    	bStringBuilder = new StringBuilder(fileStr);
		    	//System.out.println(bStringBuilder.toString());
		    }
		   co++;
		    
		}*/
	}
}
