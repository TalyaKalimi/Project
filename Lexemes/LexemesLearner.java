package Lexemes;

import Singletons.SingletonFieldsList;
import Singletons.SingletonPuncsList;

public class LexemesLearner {
	
	public LexemesLearner() {
		// TODO Auto-generated constructor stub
	}
	
	public void CreateCsvLexemes() {
		
		Lexeme lex8 = new FixedLexem(",");
		SingletonPuncsList.getInstance().add(lex8);
		
		Lexeme line = new RegexLexeme("\n", Lexeme.Category.Ignore);
		SingletonPuncsList.getInstance().add(line);
		Lexeme r = new RegexLexeme("\r", Lexeme.Category.Ignore);
		SingletonPuncsList.getInstance().add(r);
	
		Lexeme field1 = new RegexLexeme("GroupName", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field1);
		Lexeme field2 = new RegexLexeme("Groupcode", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field2);
		Lexeme field3 = new RegexLexeme("GroupOwner", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field3);
		Lexeme field4 = new RegexLexeme("GroupCategoryID", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field4);

		
	}
	
	public void CreateXmlLexemes() {
		
		//Lexeme slash = new FixedLexem("</",Lexeme.Category.Punctuation);
		Lexeme slash = new FixedLexem("/",Lexeme.Category.Punctuation);
		Lexeme left = new FixedLexem("<", Lexeme.Category.Punctuation);
		Lexeme right = new FixedLexem(">",Lexeme.Category.Punctuation);
		SingletonPuncsList.getInstance().add(slash);
		SingletonPuncsList.getInstance().add(left);
		SingletonPuncsList.getInstance().add(right);
		
		Lexeme line = new RegexLexeme("\n", Lexeme.Category.Ignore);
		SingletonPuncsList.getInstance().add(line);
		Lexeme r = new RegexLexeme("\r", Lexeme.Category.Ignore);
		SingletonPuncsList.getInstance().add(r);
		Lexeme tab = new RegexLexeme("\t", Lexeme.Category.Ignore);
		SingletonPuncsList.getInstance().add(tab);
		Lexeme whiteSpace = new RegexLexeme(" ", Lexeme.Category.Ignore);
		SingletonPuncsList.getInstance().add(whiteSpace);
		for(int i=0; i<10; i++)
		{
			SingletonPuncsList.getInstance().add(new RegexLexeme(Integer.toString(i), Lexeme.Category.Ignore));
		}
		
		/*
		Lexeme field1 = new RegexLexeme("TITLE", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field1);
		Lexeme field2 = new RegexLexeme("ARTIST", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field2);
		Lexeme field3 = new RegexLexeme("COUNTRY", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field3);
		Lexeme field4 = new RegexLexeme("COMPANY", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field4);
		Lexeme field5 = new RegexLexeme("PRICE", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field5);
		Lexeme field6 = new RegexLexeme("YEAR", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field6);
		Lexeme field7 = new RegexLexeme("CD", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field7);
		Lexeme field8 = new RegexLexeme("CATALOG", Lexeme.Category.Field);
		SingletonFieldsList.getInstance().add(field8);*/
	}

	
	public void CreateJsonLexemes() {
		char c= '\"';
		String s = Character.toString(c);
		
		Lexeme lex1 = new FixedLexem("{",Lexeme.Category.Punctuation);
		SingletonPuncsList.getInstance().add(lex1);
		Lexeme lex2 = new FixedLexem("}");
		SingletonPuncsList.getInstance().add(lex2);
		Lexeme lex3 = new FixedLexem("[");
		SingletonPuncsList.getInstance().add(lex3);
		Lexeme lex4 = new FixedLexem("]");
		SingletonPuncsList.getInstance().add(lex4);
		Lexeme lex5 = new FixedLexem(",");
		SingletonPuncsList.getInstance().add(lex5);
		Lexeme lex6 = new FixedLexem(":");
		SingletonPuncsList.getInstance().add(lex6);
		Lexeme lex7 = new FixedLexem(s);
		SingletonPuncsList.getInstance().add(lex7);
		
		Lexeme line = new RegexLexeme("\n", Lexeme.Category.Ignore);
		SingletonPuncsList.getInstance().add(line);
		Lexeme r = new RegexLexeme("\r", Lexeme.Category.Ignore);
		SingletonPuncsList.getInstance().add(r);
		Lexeme whiteSpace = new RegexLexeme(" ", Lexeme.Category.Ignore);
		SingletonPuncsList.getInstance().add(whiteSpace);
		for(int i=0; i<10; i++)
		{
			SingletonPuncsList.getInstance().add(new RegexLexeme(Integer.toString(i), Lexeme.Category.Punctuation));
		}
		
		
	}
}
