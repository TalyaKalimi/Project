package Grammar;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import org.mozilla.javascript.ast.ReturnStatement;

public class NonTerminal extends Node {

	static int nameCounter = 1;
	String name;
	protected final List<Node> productions = new ArrayList<>();

	public NonTerminal() {
		super(new Token("", 0));
		name = "N"+Integer.toString(nameCounter);
		this.token.setString(name);
		nameCounter++;
	}
	
	public void add(Node op) {
		productions.add(op);
	}

	public List<Node> getProductions() {
		return productions;
	}
	
	public String getName() {
		return name;
	}
	
	public void printProductions() {
		System.out.println();
		System.out.println(name);
		for(Node node:productions)
			System.out.print(node.getValue()+" ");
		System.out.println();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equalProduction(List<Node> other) {
		if (other.size() != productions.size())
			return false;
		for(int i=0; i<other.size(); i++) {
			if(!other.get(i).equals(productions.get(i)))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if(obj instanceof NonTerminal && super.equals(obj) && equalProduction(((NonTerminal) obj).getProductions())){
			return true;
		}
		return false;
	}
}
