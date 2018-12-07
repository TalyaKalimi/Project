package Grammar;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import All.BalancedParChecker;
import Lexemes.Lexeme;
import Lexemes.Lexeme.Category;
import Singletons.SingletonDataList;
import Singletons.SingletonFieldsList;
import Singletons.SingletonPuncsList;
import Singletons.SingletonTokenized;

/**
 * 
 * @author Talya Kalimi
 *
 */

public class TreeSkeletonLearner {

	String strFile;
	BalancedParChecker<String> balanceChecker;
	static Hashtable<String, List<Token>> BalanceHash;
	Hashtable<String, Integer> checkedForPairs;
	int numOfShows = 0;
	
	/**
	 * 
	 * @param strFile
	 */
	public TreeSkeletonLearner(String strFile) {
		this.strFile = strFile;
		this.balanceChecker = new BalancedParChecker<String>();
		TreeSkeletonLearner.BalanceHash = new Hashtable<>();
		checkedForPairs = new Hashtable<>();
	}
	
	/**
	 * Search for all possible balanced pairs in text.  
	 * To use as Open bracket and close bracket
	 */
	public void learnBalancedPairs() {

		List<String> pairsString = new ArrayList<>();
		for(Lexeme lex: SingletonTokenized.getInstance()) {
			if(lex.getCategory() == Category.Punctuation || lex.getCategory() == Category.Field )
				pairsString.add(lex.getString());
		}
		String [] pairsArr = new String[pairsString.size()];
		pairsArr = pairsString.toArray(pairsArr);
		
		searchPairs(pairsArr);
		filterBalancedPairs();
		
	}

	/**
	 * Makes possible combination for balanced pairs in text.
	 * @param pairsArr - an array of all punctuation and fields in text.
	 * By their original order
	 */
	private void searchPairs(String [] pairsArr) {
		
		String open = null;
		String close = null;
		String key = null;
		/*one by one*/
		for(int i=0; i<pairsArr.length; i++) {
			for(int j=i; j<pairsArr.length; j++) {
				open = pairsArr[i];
				close = pairsArr[j];
				key = open+close;
				if(!checkedForPairs.containsKey(key)) {
					checkedForPairs.put(key, 1);
					arePairs(open, close);
				}
			}
		}
		/* search pairs of pairs */
		List<String> pairsPairs = new ArrayList<>();
		for(int i=0; i<pairsArr.length-1; i++) {
			pairsPairs.add(pairsArr[i]+pairsArr[i+1]);
		}
		
		for(int i=0; i<pairsPairs.size(); i++) {
			for(int j=i; j<pairsPairs.size(); j++) {
				open = pairsPairs.get(i);
				close = pairsPairs.get(j);
				key = open+close;
				if(!checkedForPairs.containsKey(key)) {
					checkedForPairs.put(key, 1);
					arePairs(open, close);
				}
			}
		}
		
		List<String> threeComb = new ArrayList<>();
		/* pairs and one, three combination */
		for(int i=0; i<pairsPairs.size(); i++) {
			for(int j=i; j<pairsArr.length; j++) {
				open = pairsPairs.get(i);
				close = pairsArr[j];
				key = open+close;
				if(!checkedForPairs.containsKey(key)) {
					checkedForPairs.put(key, 1);
					if(strFile.indexOf(key) > -1)
						threeComb.add(key);
				}
				
			}
		}
		
		for(int i=0; i<threeComb.size(); i++) {
			for(int j=i; j<threeComb.size(); j++) {
				open = threeComb.get(i);
				close = threeComb.get(j);
				key = open+close;
				if(!checkedForPairs.containsKey(key)) {
					checkedForPairs.put(key, 1);
					arePairs(open, close);
				}
			}
		}
		System.out.println();
		
		
	}
	
	
	/**
	 * If the given strings are not checked yet and are balanced pair in text,
	 * add them to hash.
	 * @param open
	 * @param close
	 */
	private void arePairs(String open, String close) {
		boolean balanced = false;
			balanced = checkBalance(open, close);
			if(balanced) {
				if(BalanceHash.containsKey(open)) {
					BalanceHash.get(open).add(new Token(close,numOfShows));
				}
				else {
					List<Token> list = new ArrayList<>();
					BalanceHash.put(open, list);
					BalanceHash.get(open).add(new Token(close,numOfShows));
					
				}
				
			}
		
	}
	
	/**
	 * Check if the given strings are balanced pair in text
	 * @param open
	 * @param close
	 */
	
	private boolean checkBalance (String open, String close) {
		boolean ans = false;
		balanceChecker.insertBalancePair(open,close);
		//System.out.println("\n my pair: "+ open.getString() +" " +close.getString());
		String onlyPairs = balanceChecker.filterStr(strFile, open, close);
		if(onlyPairs.length() < 3) {
			return false;
		}
		String [] arrPairs = onlyPairs.split(",");
		//System.out.println(onlyPairs);
		//for (String cell: arrPairs)
			//System.out.print(cell + " ");
		/*System.out.print("\nBalance : ");
		System.out.print(balanceChecker.areParenthesisBalanced(arrPairs));
		System.out.println();*/
		ans = balanceChecker.areParenthesisBalanced(arrPairs);
		if(open.equals(close) && (arrPairs.length % 2) ==0)
			ans = true;
		if(ans)
			numOfShows = (arrPairs.length)/2;
		else
			numOfShows = 0;
		return ans;
	}
	
	/**
	 * All balanced pairs in text
	 * @return Hashtable<String, List<Token>>
	 */
	public static Hashtable<String, List<Token>> getBalancedPairs(){
		return BalanceHash;
	}
	
	
	/**
	 * Check if string is a number
	 * @param strNum
	 * @return boolean
	*/
   private boolean isNumeric(String strNum) {
	    try {
	        @SuppressWarnings("unused")
			double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	   
    /* arr[]  ---> Input Array 
    data[] ---> Temporary array to store current combination 
    start & end ---> Staring and Ending indexes in arr[] 
    index  ---> Current index in data[] 
    r ---> Size of a combination to be printed */
    /*static void combinationUtil(int arr[], int data[], int start, 
                                int end, int index, int r) 
    { 
        // Current combination is ready to be printed, print it 
        if (index == r) 
        { 
            for (int j=0; j<r; j++) 
                System.out.print(data[j]+" "); 
            System.out.println(""); 
            return; 
        } 
  
        // replace index with all possible elements. The condition 
        // "end-i+1 >= r-index" makes sure that including one element 
        // at index will make a combination with remaining elements 
        // at remaining positions 
        for (int i=start; i<=end && end-i+1 >= r-index; i++) 
        { 
            data[index] = arr[i]; 
            combinationUtil(arr, data, i+1, end, index+1, r); 
        } 
    }*/
    
	/**
	 * Choose the relevant pairs by number of filters. 
	 */
	
    public void filterBalancedPairs() {
		
		 /**remove from file all the ignore puncs*/
		String tempFile = strFile;
		List<String> toRemoveList = new ArrayList<String>();
		List<Token> toAdd = new ArrayList<Token>();
		Token tokenToAdd = null;
		
		for(Lexeme punc: SingletonPuncsList.getInstance()) {
			if(punc.getCategory() == Category.Ignore && (!isNumeric(punc.getString())
					&& punc.getString()!= " ")){
				tempFile = tempFile.replaceAll(punc.getString(), "");
			}
		}
		
		int openIndex = 0;
		int closeIndex = 0;
		String open = null;
		String close = null;
		String wordBetween = null;
		for (Entry<String, List<Token>> entry:BalanceHash.entrySet()) {
			open = entry.getKey();
			openIndex = tempFile.indexOf(open);
			for(Token token: entry.getValue()) {
				close = token.getString();
				closeIndex = tempFile.indexOf(close);
				if(open.equals(close)) {
					closeIndex = (tempFile.substring(closeIndex+1, tempFile.length()).indexOf(close)+closeIndex+1);
				}
				if(closeIndex > 0 && openIndex > 0 && openIndex+open.length() < tempFile.length() && openIndex+open.length() < closeIndex) {
					wordBetween = tempFile.substring(openIndex+open.length(), closeIndex);
					// distance one
					if(wordBetween!= null && 
							(SingletonFieldsList.getHashInstance().containsKey(wordBetween) ||
									SingletonDataList.getHashInstance().containsKey(wordBetween)||
									SingletonPuncsList.getHashInstance().containsKey(wordBetween))){
						tokenToAdd = new Token(close, closeIndex);
						toAdd.add(tokenToAdd);
					}
				}
			}
			if(toAdd.size() < 1)
				toRemoveList.add(open);
			
			entry.getValue().clear();
			entry.getValue().addAll(toAdd);
			toAdd.clear();
			
		}
		for(String strRemove: toRemoveList) {
			BalanceHash.remove(strRemove);
		}
		
		System.out.println("---- parentheses ----");
    	Token tok = null;
    	for (Entry<String, List<Token>> entryHash:BalanceHash.entrySet()) {
			 System.out.println();
			 System.out.println("open: "+entryHash.getKey());
			 System.out.print("close: [");
			 for(int  i=0; i<entryHash.getValue().size()-1; i++) {
				 tok = entryHash.getValue().get(i);
				 System.out.print(tok.getString()+ ":"+tok.getPosition()+" , ");
			 }
			 tok = entryHash.getValue().get(entryHash.getValue().size()-1);
			 System.out.println(tok.getString()+ ":"+tok.getPosition()+" ]");
    	}
		
		/*int distance = 0;
    	
		//Hashtable<String, Integer> toAddHash = new Hashtable<>();
		
		//Remove repeated keys that contains each other ; like "<TITLE" "<TITLE>" 
		 // TODO: maybe do it earlier to compute less cases 
		int listSize = SingletonTokenized.getInstance().size();
		String keyToCheck = null;
		String balancePair = null;
		List<Token> keyPairsList = null;
		*/
		 
		/*
		for (int i=0; i< listSize-distance ; i++) {
			keyToCheck = SingletonTokenized.getInstance().get(i).getString();
				if (BalanceHash.containsKey(keyToCheck)) {
					balancePair = SingletonTokenized.getInstance().get(i+distance).getString();
					keyPairsList = BalanceHash.get(keyToCheck);
					if((tokenToAdd = listContainString(keyPairsList, balancePair)) != null) {
						toAdd.add(tokenToAdd);
					}
				}
				keyPairsList.clear();
				keyPairsList.addAll(toAdd);
				toAdd.clear();
		}*/
		
		/*FIRST STEP - find pairs that have 0-1 distance*/
		/*
		List<String> toRemoveList = new ArrayList<String>();	
		for (Entry<String, List<Token>> entry:BalanceHash.entrySet()) {
			for (Entry<String, List<Token>> toRemove:BalanceHash.entrySet()) {
				if(!entry.getKey().equals(toRemove.getKey()) && entry.getKey().indexOf(toRemove.getKey()) > -1) {
					toRemoveList.add(toRemove.getKey());
				}
			}
		}
		for(String strRemove: toRemoveList) {
			BalanceHash.remove(strRemove);
		}
		*/
		
		/* check distance between open an close. if there is one up-to-distance tokens between them it's ok*/
		/*toRemoveList.clear();
		
		boolean found = false;
    	for (Entry<String, List<Token>> entry:BalanceHash.entrySet()) {
			 for(int i=0; i<SingletonTokenized.getInstance().size()-distance && !found ; i++) {
				// check if ( is part of the tokenized.  using indexOf because it contains puncs. "<TITLE>" is ( and "TITLE" in tokenized.
				 if(entry.getKey().indexOf(SingletonTokenized.getInstance().get(i).getString()) > -1) {	
					 found = true;
					 for(Token token: entry.getValue()) {
						 for(int dis=2; dis<=distance; dis++) {
							 if(token.getString().indexOf(SingletonTokenized.getInstance().get(i+dis).getString()) > -1) {
								 if(!toAddHash.containsKey(token.getString())) {
									 toAddHash.put(token.getString(), 1);
									 toAdd.add(token);
								 }	 
							 }
						 }
						
					 }
				 }
			 }
			 found = false;
			 entry.getValue().clear();
			 entry.getValue().addAll(toAdd);
			 if(toAdd.size() < 1) {
				 toRemoveList.add(entry.getKey());
			 }
			 toAdd.clear();
			 toAddHash.clear();
		}
    	
    	for(String strRemove: toRemoveList) {
			BalanceHash.remove(strRemove);
		}
    	*/
    	
    }
    
    /*private Token listContainString (List<Token> list, String str) {
		for(Token token: list) {
			if(token.getString().equals(str)) {
				return token;
			}
		}
		return null;
	}*/
    
	
    
	/* public void learnBalancedPairs() {
	 * int i = 0;
	boolean balanced = false;
	int listSize = SingletonPuncsList.getInstance().size();
	Lexeme open;
	Lexeme close;
	while (i < listSize)
	{
		open = SingletonPuncsList.getInstance().get(i);
		if (open.getCategory() != Category.Ignore) {
			for (int j = 0; j< listSize; j++) {
				close = SingletonPuncsList.getInstance().get(j);
				if (close.getCategory() != Category.Ignore) {
					balanced = checkBalance(open, close);
					if(balanced) {
						SingletonPuncsList.getHashInstance().get(open.getString()).setCategory(Category.OpenBarcket);
						SingletonPuncsList.getHashInstance().get(close.getString()).setCategory(Category.CloseBarcket);
						BalanceHash.put(open.getString(), close.getString());
					}
				}
			}
		}
		i++;
	}*/
	/*List<Lexeme> combined = SingletonPuncsList.getInstance();
	combined.addAll(SingletonFieldsList.getInstance());
	List<String> stringList = new ArrayList<>();
	for(Lexeme l: combined) {
		if(l.getCategory()!= Category.Ignore)
			stringList.add(l.getString());
		}
	
	searchPairs(stringList,2);
	searchPairs(stringList, 3);*/
	/*
	System.out.println();
	System.out.println("CHECK");
	String fileStrBld = strFile;
	for(Lexeme sss:SingletonTokenized.getInstance()) {
		if(isNumeric(sss.getString()) || 
				(sss.getString().length() > 1 && sss.getCategory() != Category.Field && sss.getCategory() != Category.Punctuation))
			fileStrBld = fileStrBld.replace(sss.getString(), "");
	}
	System.out.println(fileStrBld);
	
	List<Lexeme> combined = SingletonPuncsList.getInstance();
	combined.addAll(SingletonFieldsList.getInstance());
	List<String> stringList = new ArrayList<>();
	for(Lexeme l: combined) {
		if(l.getCategory()!= Category.Ignore)
			stringList.add(l.getString());
	}
	
	
	Collections.sort(stringList, new Comparator<String>() {
        public int compare(String c1, String c2) {
            return c2.length()- c1.length();
        }
       });
	
	System.out.print("SORTED List: ");
	for (String str : stringList) {
		System.out.print(str + ", ");
	}
	
	String [] arr = null;
	List<String> asList = new ArrayList<>();
	String [] subArr = null;
	int size = stringList.size();
	int i =0;
	String str = null;
	str = stringList.get(i);
	arr = fileStrBld.split("((?<="+str+")|(?="+str+"))");
	while(i < size-1) {
		i++;
			str = stringList.get(i);
			if(!str.equals(".") && !str.equals("{") && !str.equals("}")) {	//TODO: how to solve { } 
				for(int j=0; j<arr.length; j++) {
						subArr= arr[j].split("((?<="+str+")|(?="+str+"))");
						for(String s:subArr)
							asList.add(s);
				}
				arr= asList.toArray(arr);
				asList.clear();
			}
	}
	
	
	System.out.println();
	System.out.println("ARRAY");
	for(String stttr: arr)
		System.out.print("["+ stttr + "] ");
	
	List<Lexeme> pairs = new ArrayList<>();
	for(String element: arr) {
		if(SingletonFieldsList.getHashInstance().containsKey(element)) {
			pairs.add(new RegexLexeme(element,Category.Field));
		}
		else if (SingletonPuncsList.getHashInstance().containsKey(element)) {
			pairs.add(new RegexLexeme(element,Category.Punctuation));
		}
	}
	
	/**get together < \  to one token <\ */
	/*String twoPuncs = null;
	for(int index=0; index<pairs.size()-1; index++) {
		twoPuncs = pairs.get(index).getString()+pairs.get(index+1).getString();
		if(SingletonPuncsList.getHashInstance().containsKey(twoPuncs)) {
			pairs.set(index, new RegexLexeme(twoPuncs, Category.Punctuation));
			pairs.remove(index+1);
		}
	}
	
	*/
	//for(Lexeme lex:pairs) {
	//	System.out.println(lex.getString() + "  : "+lex.getCategory());
	//}
	/*Collections.sort(SingletonPuncsList.getInstance(), new Comparator<Lexeme>() {
        public int compare(Lexeme c1, Lexeme c2) {
            return c2.getString().length()- c1.getString().length();
        }
       });
	
	System.out.print("SORTED PUNCS List: ");
	for (Lexeme lexeme : SingletonPuncsList.getInstance()) {
		System.out.print(lexeme.getString() + ", ");
	}
	*/
	
	/*for (Lexeme punc : SingletonPuncsList.getInstance()) {
		if (punc.getCategory() != Category.Ignore)
			System.out.println(Arrays.toString(fileStrBld.split("((?<="+punc.getString()+")|(?="+punc.getString()+"))")));
	}*/
	
	//String [] arr = fileStrBld.split("((?<="+punc+")|(?="+punc+"))");
	
	/*
	String str = null;
	Hashtable<String, Integer> helpHash = new Hashtable<>();
	List<Token> mylist = new ArrayList<Token>();
	int index = 0;
	Token token = null;
	 for(Lexeme field: SingletonFieldsList.getInstance()) {
		 int jump=0;
		 str=field.getString();
		 if(helpHash.containsKey(str))
			 jump = helpHash.get(str);
		 index=fileStrBld.indexOf(str, jump+1);
		 while(index > -1) {
			 token = new Token(str, index, Category.Field);
			 mylist.add(token);
			 helpHash.put(str, index);
			 index=fileStrBld.indexOf(str, index+str.length());
		 }
	 }
	
	 for(Lexeme punc: SingletonPuncsList.getInstance()) {
		 if(punc.getCategory()!= Category.Ignore) {
			 int jump=0;
			 str=punc.getString();
			 if(helpHash.containsKey(str))
				 jump = helpHash.get(str);
			 index=fileStrBld.indexOf(str, jump+1);
			 while(index > -1) {
				 token = new Token(str, index, Category.Punctuation);
				 mylist.add(token);
				 helpHash.put(str, index);
				 index=fileStrBld.indexOf(str, index+str.length());
			 }
		 }
	 }
	System.out.println();

	 if (!mylist.isEmpty()) {
		    Collections.sort(mylist, new Comparator<Token>() {
		        @Override
		        public int compare(Token c1, Token c2) {
		            return c1.getPosition()- c2.getPosition();
		        }
		       });
		   }
	 
	 for(Token t: mylist) {
		  System.out.println("Pos:"+ t.getPosition()+" "+t.getToken()+ "\t\t"+ t.getCategory());
	 }
	*/
	//String[] arr = stringList.toArray(new String[stringList.size()]);
	//printCombination(arr,2);
	//printCombination(arr,3);
}
