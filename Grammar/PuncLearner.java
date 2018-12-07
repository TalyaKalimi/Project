package Grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import All.CreateHtmlDocument;
import Lexemes.Lexeme;
import Lexemes.RegexLexeme;
import Singletons.SingletonPuncsList;

/**
 * 
 * @author Talya Kalimi
 *
 */

public class PuncLearner {

	String myFile;
	
	/**
	 * 
	 * @param str - file as string
	 */
	public PuncLearner(String str) {
		myFile = str;
	}
	
	/**
	 * learn Punctuation process
	 */
	public void learnPuncs() {
		
		String fileToString = "";
		String subStr = "";
		ArrayList<String> puncArr = new ArrayList<String>();
		String arrayToHtml = "var x = [";
		int len = 0;
		Hashtable <String,Integer> puncHash =new Hashtable<String,Integer>();

		fileToString = myFile;
		len = fileToString.length();
		
		// divide to all substrings
		for (int i = 0; i < len; i++)
		{
			for(int j = 1; j <= len - i; j++)
		    {
		    	subStr = fileToString.substring(i, i+j);
		    	
		    	if (subStr.charAt(0)== ' ' || subStr.charAt(subStr.length()-1)== ' ')
		    		break;
		    	
		    	// ignore " . TODO : why html\js can't use it.
		    	if (subStr.length()==1 && subStr.indexOf('"')>=0)
		    		break;
		    	
		    	if(subStr.length() < 3 && !subStr.contains("\r") && !subStr.contains("\n")  && !subStr.contains(".")&& !subStr.contains("\"")) {
		    		if(!subStr.matches(".*[a-zA-Z]+.*") && !subStr.matches(".*\\d+.*")) {
		    		arrayToHtml = insertToArray(arrayToHtml,subStr);
		    		puncArr.add(subStr);
		    		}
	    		}
	    	 }
		}	
		if (arrayToHtml != "var x = [")
			arrayToHtml = arrayToHtml.substring(0,arrayToHtml.length()-2);
		arrayToHtml = arrayToHtml + "];";
		CreateHtmlDocument create = new CreateHtmlDocument();
		create.createHtml(arrayToHtml);
		
		
		//System.out.println("Punctuation : " + puncArr.toString()); 
		
		int freq =0;
		for(String punc: puncArr) {
			if (puncHash.containsKey(punc)) {
	    		freq = puncHash.get(punc); 
	    		puncHash.replace(punc, freq+1);
	    	}
	    	else
	    		puncHash.put(punc, 1);
		}
		
		int max = 0;
		int secMax= 0;
		int currFreq= 0;
		int numOfCandidate = 0;
		int minFreq = Integer.MAX_VALUE;
		List<String> head = new ArrayList<String>();
		
		for (String key : puncHash.keySet()) {
			currFreq = puncHash.get(key);
		    if(currFreq > max) {
		    	secMax = max;
		    	max = currFreq;
		    }
		    else if (currFreq > secMax) {
				secMax = currFreq;
			}
		    if(currFreq < minFreq) {
		    	minFreq = currFreq;
		    }
		    numOfCandidate++;
		}
		
		/*int sqrt = (int)Math.sqrt(secMax)-1;
		
		for (String key : puncHash.keySet()) {
			if(puncHash.get(key) >= sqrt) {
					RegexLexeme newPunc = new RegexLexeme(key, Lexeme.Category.Punctuation);
					SingletonPuncsList.getInstance().add(newPunc);
					SingletonPuncsList.getHashInstance().put(newPunc.getString(), newPunc);
				}
				
			}
		}*/
	
		List<Pair> pairs = new ArrayList<>();
		for (String key : puncHash.keySet()) {
			pairs.add(new Pair(key, puncHash.get(key)));
		}
		if (!pairs.isEmpty()) {
		    Collections.sort(pairs, new Comparator<Pair>() {
		        @Override
		        public int compare(Pair c1, Pair c2) {
		            //You should ensure that list doesn't contain null values!
		            return c2.getFreq() - c1.getFreq();
		        }
		       });
		}
	
		int median = 0;
		secMax= 0;
		currFreq= 0;
		int treshhold =  Math.max(4,(int) (numOfCandidate*0.4));
		while(head.size() < treshhold) {
			median = (minFreq+max)/2;
			for (Pair p : pairs) {
				if(!head.contains(p.getString())) {
					if(p.getFreq() > secMax && p.getFreq() < max) {
						secMax = p.getFreq();
					}
					if(p.getFreq() >= median && head.size() < treshhold) {
						head.add(p.getString());
					}
				}
			}
			max = secMax;
			secMax= 0;
		}
		
		for (String key : head) {
			RegexLexeme newPunc = new RegexLexeme(key, Lexeme.Category.Punctuation);
			SingletonPuncsList.getInstance().add(newPunc);
			SingletonPuncsList.getHashInstance().put(newPunc.getString(), newPunc);	
			}
	}
	
	/**
	 * Add string str to Array arr in HTML format
	 * @param arr
	 * @param str
	 * @return
	 */
	private static String insertToArray(String arr , String str) {
		arr = arr + "\""+str+"\", ";
	return arr;
	}

}
