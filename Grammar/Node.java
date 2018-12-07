package Grammar;

public class Node {

	Token token;
	
	public Node(Token token) {
		this.token = new Token(token);
	}

	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public String getValue() {
		return token.getString();
	}
	
	public void printProductions() {
		System.out.print(token.getString()+ " ");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if(obj instanceof Node) {
			Node objNode = (Node)obj;
			if (objNode.token.getString().equals(this.token.getString()))
				return true;
		}
		
		return false;
	}
}
