package Lexemes;

	
public abstract class Lexeme {


public static enum Category {
		None, Punctuation, Field, Data, Ignore, OpenBarcket, CloseBarcket;
	}

	protected String lex;
	protected Category category;
	
	public Lexeme() {
		
		this.category = Category.None;
	}
	
	public Lexeme(String lex) {
		this.lex = lex;
		this.category = Category.None;
	}
	
	public Lexeme(String lex, Category category) {
		this.lex = lex;
		this.category = category;
	}
	
	public String getString() {
		return this.lex;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
}
