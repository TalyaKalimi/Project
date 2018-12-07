package Singletons;
import java.util.Hashtable;
import java.util.concurrent.CopyOnWriteArrayList;

import Lexemes.Lexeme;
public class SingletonFieldsList {
	
	   private static class SingleList{
		   
	        static CopyOnWriteArrayList<Lexeme> list= new CopyOnWriteArrayList<>();
	        static Hashtable<String, Lexeme> hashtable = new Hashtable<>();
	    }
	    
	    public static CopyOnWriteArrayList<Lexeme> getInstance(){
	        return SingleList.list;
	    }
	    
	    public static Hashtable<String, Lexeme> getHashInstance(){
	    	 return SingleList.hashtable;
	    }

}
