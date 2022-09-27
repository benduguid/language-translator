package refurbishedTranslator;

import java.util.Map;
import java.util.Scanner;

/**
 * Menu for Translator program
 * @author Group 5
 */
public class Menu {
	
	Translator myTranslator = new Translator(); 	//Create instance of the Translator class
	
	public static void main(String[] args) {
		Menu menu = new Menu();
		
		menu.process();
	}


	private void process() {
		runMenu();
	}

	/**
	 * Displays choices to user
	 */
	public void displayChoices() {
		
		System.out.println("");
		System.out.println("Please select one of the options below");
		System.out.println("1. Translate a phrase");
		System.out.println("2. Translate a passage of text from a text file (.txt)");
		System.out.println("3. Choose Langauge");
		System.out.println("0. Exit");
		System.out.println("");
		
	}
	
	public void runMenu() {
		String userChoice = "";
		
		Scanner scan = new Scanner(System.in);
		
		do {
			displayChoices();
			userChoice = scan.nextLine();
			
			switch(userChoice) {
			
			case "1":
				Map<String, Integer> firstLanguageHashMap = myTranslator.firstLanguageToHashMap();		//loads first language HashMap
				Map<Integer, String> secondLanguageHashMap = myTranslator.secondLanguageToHashMap();	//loads second language HashMap
				String[] phraseArray = myTranslator.stringToArray();	//Turns sentence into array of words
				int[] keys = myTranslator.getKeys(phraseArray, firstLanguageHashMap, secondLanguageHashMap);	//Gets keys that the words correspond in the HashMap into an array of keys	
				break;
			case "2":
				myTranslator.languageChoice();
				Map<String, Integer> firstTextFileHashMap = myTranslator.firstLanguageToHashMap();	//loads hashmap
				Map<Integer, String> secondTextFileHashMap = myTranslator.secondLanguageToHashMap();
				myTranslator.textFileToArray(firstTextFileHashMap,secondTextFileHashMap);		//Calls method to translate text file
				break;
			case "3":
				myTranslator.languageChoice();	//Displays choice of language to the user to translate from and to
				break;
			case "0":
				System.out.println("Goodbye!");
				break;
			default:
				System.out.println("Sorry. Unrecognised Choice\n Try again?");
				break;
			}
		}while(!userChoice.equals("0"));
				
	}

}
