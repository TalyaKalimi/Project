package All;
import java.util.Hashtable;
import java.util.Stack;

/**
 * 
 * @author Talya Kalimi
 *
 * @param <T>
 */

public class BalancedParChecker <T> {

	Stack<T> stack;
	Hashtable<T, T> hash;
	
	public BalancedParChecker() {
		
		 this.stack = new Stack<>();
		 this.hash = new Hashtable<>();
	}
		
	
	public void insertBalancePair(T open, T close)
	{
		this.hash.clear();
		this.stack.clear();
		hash.put(open, close);
	}
  /* Returns true if character1 and character2
   are matching left and right Parenthesis */
    public boolean isMatchingPair(T open, T close) 
    {
      return (hash.get(open).equals(close));
    }
    
    
    /* Return true if expression has balanced 
    Parenthesis */
    public boolean areParenthesisBalanced(T exp[])
    {
	    /* Traverse the given expression to 
	       check matching parenthesis */
	    for(int i=0;i<exp.length;i++)
	    {
	        
	       /*If the exp[i] is a starting 
	         parenthesis then push it*/
	       if (hash.containsKey(exp[i]))
	       {
	    	   stack.push(exp[i]);
	       }
	         
	   
	       /* If exp[i] is an ending parenthesis 
	          then pop from stack and check if the 
	          popped parenthesis is a matching pair*/
	       else if (hash.contains(exp[i]))
	       {
	               
	           /* If we see an ending parenthesis without 
	              a pair then return false*/
	          if (stack.isEmpty())
	            {
	                return false;
	            } 
	   
	          /* Pop the top element from stack, if 
	             it is not a pair parenthesis of character 
	             then there is a mismatch. This happens for 
	             expressions like {(}) */
	          else if (!isMatchingPair(stack.pop(), exp[i]))
	            {
	                return false;
	            }
	       }
	        
	    }
	    
	    /* If there is something left in expression 
        then there is a starting parenthesis without 
        a closing parenthesis */
     
     if (stack.isEmpty())
       return true; /*balanced*/
     else
        /*not balanced*/
           return false;
       
	}
    
   /* public String filterStr(String str, String word) {
        StringBuilder s = new StringBuilder(str);

        // Find substrings to redact, from index i (inclusive)
        // to index j (exclusive)
        for (int i = 0; i < s.length(); i += word.length()) {
            int j = s.indexOf(word, i);
            if (j < 0) {
                // word not found; redact the rest of the string
                j = s.length();
            }

            while (i < j-1) {
                s.setCharAt(i++, ' ');
            }
            s.setCharAt(i++, ',');
        }
        return (s.toString()).replaceAll("\\s+","");
    }*/
    
    public String filterStr(String str, String... words) {
        StringBuilder s = new StringBuilder(str);

        // Find substrings to redact, from index i (inclusive)
        // to index j (exclusive)
        int i = 0;
        int len = 0;
        boolean first = true;
        boolean start = true;

        while (i < s.length()) {
        	int j = -1;
        	int minIndex = Integer.MAX_VALUE;
        	for(String word : words)
        	{
        		j = s.indexOf(word, i);

        		if (start || (j != -1 && j < minIndex)) {
        			start = false;
        			minIndex = j;
        			len = word.length();
        		}
        	}
        	if (minIndex != Integer.MAX_VALUE)
        		j = minIndex;
        	
            if (j < 0) {
                // word not found; redact the rest of the string
                j = s.length();
            }

            while (i < j-1) {
                s.setCharAt(i++, ' ');
            }
            
            if(!first && i < j)
            	s.setCharAt(i++, ',');
            if (first && i < j)
            	s.setCharAt(i++, ' ');	

            
            i = i+len;
            first = false;
        }
        return (s.toString()).replaceAll("\\s+","");
    }
}

