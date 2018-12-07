import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.xml.transform.Templates;

import Grammar.FieldsLearner;
import Grammar.GrammarLearner;
import Grammar.Node;
import Grammar.NonTerminal;
import Grammar.PuncLearner;
import Grammar.Token;
import Grammar.TreeSkeletonLearner;
import Lexemes.FixedLexem;
import Lexemes.Lexeme;
import Lexemes.Lexeme.Category;
import Lexemes.RegexLexeme;
import Singletons.SingletonDataList;
import Singletons.SingletonFieldsList;
import Singletons.SingletonPuncsList;

public class main {

	
	static ArrayList<Lexeme> options = new ArrayList<Lexeme>();
	
	public static void main(String[] args) {
	
		Scanner scanner = new Scanner(System.in);
		String [] arr;
		String input = null;
		String fileType = null;
		FileFactory filesFactory = new FileFactory();
		String file = null;
		
		 System.out.println("Please enter file name...");
         input = scanner.nextLine();
     	fileType = input;
     	try {
     		file = filesFactory.openUserFile(fileType);
     		String[] temp = fileType.split("\\.");
     		fileType = temp[temp.length-1];
		} catch (Exception e) {
			System.out.println("Can't open this file");
			return;
		}
     	
        while (true) {
        	
            System.out.print("Please enter your option:\nOPTION CATEGORY (Category = Punc, Ignore, Field, Data)\n"
            		+ "To finish enter \"quit\"\n");
             input = scanner.nextLine();

            if ("quit".equals(input)) {
                System.out.println("Start Inffering grammar...");
                break;
            }

            else {
            	arr = input.split(" ");
            	if(arr.length > 1)
            		fillUserOption(arr[0], arr[1]);
            }
            
            System.out.println("input : " + input);
            System.out.println("-----------\n");
        }

        scanner.close();
	 	
        long startTime = System.nanoTime();
        
		fillOptions(fileType);
	
		PuncLearner learner = new PuncLearner(file);
		learner.learnPuncs();		
		
		
		TextScanner textScanner = new TextScanner(file);
		textScanner.firstScanFile();
		
		
		textScanner.ScanFile();
		
		FieldsLearner fieldsLearner = new FieldsLearner(file);
		System.out.println("----");
		fieldsLearner.LeranFields();
		
		PrintLexemesLists();
	
		TreeSkeletonLearner treeLearner = new TreeSkeletonLearner(file);
		treeLearner.learnBalancedPairs();
	
		GrammarLearner grammarLearner = new GrammarLearner();
		
		long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("\nTotal Runtime : "+ totalTime/1e9 + " sec");
	}

	private static void PrintLexemesLists() {
		System.out.print("Punctuation List: ");
		for (Lexeme lexeme : SingletonPuncsList.getInstance()) {
			if (lexeme.getCategory()!= Lexeme.Category.Ignore)
					System.out.print(lexeme.getString() + " ");
		}
		System.out.println();
		
		System.out.print("Fields List: ");
		for (Lexeme lexeme : SingletonFieldsList.getInstance()) {
			System.out.print(lexeme.getString() + ", ");
		}
		System.out.println();
		
	}

	private static void fillOptions(String fileType) {
		
		if (fileType.equals("json")) {
			char c= '\"';
			String s = Character.toString(c);
			Lexeme lex7 = new FixedLexem(s);
			options.add(lex7);
			SingletonPuncsList.getHashInstance().put(lex7.getString(), lex7);
		}
		
		Lexeme line = new RegexLexeme("\n", Lexeme.Category.Ignore);
		options.add(line);
		Lexeme r = new RegexLexeme("\r", Lexeme.Category.Ignore);
		options.add(r);
		Lexeme tab = new RegexLexeme("\t", Lexeme.Category.Ignore);
		options.add(tab);
		Lexeme whiteSpace = new RegexLexeme(" ", Lexeme.Category.Ignore);
		options.add(whiteSpace);
		for(int i=0; i<10; i++)
		{
			options.add(new RegexLexeme(Integer.toString(i), Lexeme.Category.Ignore));
		}
		addOptionsToList();
	}
	
	private static void fillUserOption(String option, String category) {
		Lexeme toAdd = null;
		switch (category) {
		case "Punc":
			toAdd = new RegexLexeme(option, Lexeme.Category.Punctuation);
			SingletonPuncsList.getInstance().add(toAdd);
			break;
		case "Field":
			toAdd = new RegexLexeme(option, Lexeme.Category.Field);
			SingletonFieldsList.getInstance().add(toAdd);
			break;
		case "Ignore":
			toAdd = new RegexLexeme(option, Lexeme.Category.Ignore);
			SingletonPuncsList.getInstance().add(toAdd);
			break;
		case "Data":
			toAdd = new RegexLexeme(option, Lexeme.Category.Data);
			SingletonDataList.getInstance().add(toAdd);
			break;
		default:
			break;
		}
	}
	
	
	private static void addOptionsToList() {
		
		for(Lexeme lex: options) {
			SingletonPuncsList.getInstance().add(lex);
			//SingletonPuncsList.getHashInstance().put(lex.getString(), lex);
		}
	}

}
