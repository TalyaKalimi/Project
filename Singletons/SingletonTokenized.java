package Singletons;
import java.util.concurrent.CopyOnWriteArrayList;

import Lexemes.Lexeme;

public class SingletonTokenized {
	
	   private static class SingleList{
		   
	        static CopyOnWriteArrayList<Lexeme> list= new CopyOnWriteArrayList<>();
	    }
	    
	    public static CopyOnWriteArrayList<Lexeme> getInstance(){
	        return SingleList.list;
	    }
}
