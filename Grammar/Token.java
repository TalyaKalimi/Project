package Grammar;

import Lexemes.Lexeme.Category;

public class Token {

	private String token;
	private int position;
	private Category category;
	
	public Token(String token, int position, Category category) {
		this.token = token;
		this.position = position;
		this.category = category;
	}
	
	public Token(String token, Category category) {
		this.token = token;
		this.category = category;
	}
	
	public Token(String str, int pos) {
		token = str;
		position = pos;
	}

	public Token(Token token){
		this.token = token.token;
		this.position = token.position;
		this.category = token.category;
	}
	
	public int getPosition() {
		return position;
	}
	
	public String getString() {
		return token;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public void setString(String token) {
		this.token = token;
	}
}
