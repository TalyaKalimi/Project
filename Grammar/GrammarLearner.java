package Grammar;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/***
 * 
 * @author Talya Kalimi
 *
*/

public class GrammarLearner {

	List<Token> fileList;
	
	/**
	 * 
	 * @param list
	 */
	public GrammarLearner(List<Token> list) {
		fileList = list;
	}
	
	public GrammarLearner() {
		// TODO Auto-generated constructor stub
	}
	
	public void learn() {
		Hashtable<String, List<Token>> balance = TreeSkeletonLearner.getBalancedPairs();
		
		List<Node> grammar = new ArrayList<>();
		for (Entry<String, List<Token>> entry:balance.entrySet()) {
			 System.out.println();
			 System.out.println("key-: "+entry.getKey());
			 System.out.println("value-: "+entry.getValue());

		 }
		NonTerminal nonTerminal = null;
		boolean start = false;
		
		for(Token token: fileList) {
			if(balance.containsKey(token.getString())) {
				start = true;
				nonTerminal = new NonTerminal();
				nonTerminal.add(new Node(token));
				grammar.add(nonTerminal);
			}
			else if (start && balance.containsKey(token.getString())) {
				nonTerminal.add(new Node(token));
				start = false;
			}
			else if (start && nonTerminal!= null) {
				nonTerminal.add(new Node(token));
			}
			else {
				grammar.add(new Node(token));
			}
		}
		
		for(Node node:grammar) {
			node.printProductions();
		}
	}
	
	/**
	 * Check merge rules for two Non-Terminals
	 * 1. A->aBc A->aDc  => B=D
	 * 2. A->a B->a  => A=B
	 * @param n1
	 * @param n2
	 */
	public void mergeNonTerminal(NonTerminal n1, NonTerminal n2) {
		
		List<Node> n1List = n1.getProductions();
		List<Node> n2List = n1.getProductions();
		int n1Size = n1List.size();
		int n2Size = n2List.size();
		boolean foundNonTerminal = false;
		Node nodeN1, nodeN2, nodeToReplace, nodeToSave = null;
		
		if (n1Size == n2Size) {
			for(int i=0; i<n1Size; i++) {
				nodeN1 = n1List.get(i);
				nodeN2 = n2List.get(i);
				if(!(nodeN1 instanceof NonTerminal) && !(nodeN2 instanceof NonTerminal)) {
					if(!nodeN1.getValue().equals(nodeN2.getValue())) {
						break;
					}
				}
				if((nodeN1 instanceof NonTerminal) && (nodeN2 instanceof NonTerminal)) {
					if(foundNonTerminal)
						break;
					foundNonTerminal = true;
					nodeToReplace = nodeN2;
					nodeToSave = nodeN1;
				}
				// last iteration, found one of the rules
				if(i == n1Size - 1) {
					
					// rule A->aBc A->aDc  => B=D
					if(n1 == n2 && foundNonTerminal)
						nodeToReplace = nodeToSave;
					
					// rule A->a B->a  => A=B
					if(n1 != n2 && !foundNonTerminal)
						n2 = n1;
				}
					
			}
			
		}
	}
}
