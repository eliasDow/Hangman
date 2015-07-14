import java.util.*;
import java.io.*;
/**
* Program that plays the game hangman
* reads in a word from a file and ask the user to guess the word
* Project 2 Alternate
* Elias Dowling-Huppert
*/

public class Project2 {
	static ArrayList<String> wordArrayList;
	static Scanner kb;
	static int guess;
	static String letter;
	static String arrayString;
	static String[] wordArray;
	static String[] guessed;
	
	public static void main(String [] args) throws FileNotFoundException{
		File file = null;
		String filename = null;
		// checks if file exists
		do{
			System.out.print("File with hangman words: ");		
			kb = new Scanner(System.in);
			filename = kb.nextLine();
			file = new File(filename);
		
		} while(!file.exists());
				
		wordArrayList = new ArrayList<String>();
		Scanner keyboard = new Scanner(file);
		//reads in words from file
		while (keyboard.hasNext()){
			wordArrayList.add(keyboard.nextLine());
		}
		keyboard.close();

		// calls game method
		game();
	}
	/**
	* Plays a round of hangman
	* Calls multiple methods
	*/
	public static void game(){
		// takes first line of file and makes it into a string
		arrayString = wordArrayList.get(0);
		wordArray = new String[arrayString.length()];
		guessed = new String[arrayString.length()];
		// calls method to create array of letters
		arrayCreator();
		// makes all indexes in guessed array underscores 
		for (int y = 0; y <guessed.length; y++){
			if (wordArray[y].equals(" ")){
				guessed[y] = " ";
			}
			else guessed[y] = "_";
		}
		int found=0;
		int wrong = 0;
		// ArrayList used to check if user has guessed letter before
		ArrayList<String> lettersGuessed = new ArrayList<String>();
		// ArrayList to print out previously wrong guessed letters 
		ArrayList<String> wrongGuess = new ArrayList<String>();
		
		//prints out difficulty of word
		//calls difficulty method
		System.out.printf("\nThe difficulty of the word is %.4f\n", difficulty(wordArray));
		
		// loop that actually plays the game
		for (guess=8; guess <= 8 && guess >= 1;guess--){
			//calls currentWord method
			currentWord();
			System.out.print("\nWrong guesses "+wrong+"/8:");
			//call printGuess method to print out wrong guesses
			printGuess(wrongGuess);
			System.out.println("\nGuess: ");
			letter = kb.next();
			
			//calls search method
			found = search(letter);	
			
			if (lettersGuessed.contains(letter)) {
				System.out.print("You already guessed \'"+letter+"\'!\n");
				guess++;
			}
			else if (found == 1){
				guess++; //so program doesn't count correct guess as wrong 
				correctLetter();
			}
			else{
				wrong++;
				wrongGuess.add(letter);
				if (guess==1){
					System.out.println("Sorry, you lost the game. The correct word or phrase was: ");
					System.out.println(arrayString);
					break;
				}
			}
			
			if(Arrays.equals(wordArray,guessed)){
				System.out.println("Congratulations, you guessed the word!");
				break;
			}
			lettersGuessed.add(letter);
		}
	}
	
	/**
	* Makes an array the length of String arrayString to hold a char at each index
	*
	*/
	public static void arrayCreator(){
		for (int i = 0; i<arrayString.length();i++ ){
			char k = arrayString.charAt(i); // assigns char at q to variable k
			wordArray[i] = Character.toString(k); //converts k to a string and assigns it to spot in wordArray
		}
	}
	/**
	* Searches array wordArray for guessed letter
	* @param let String that holds word that is being guessed
	* @return found Returns either 1 or 0 to indicate whether the letter was found or not
	*/
	public static int search(String let){
		int found = 0;
		for (int i = 0; i< wordArray.length; i++){
			if (wordArray[i].equalsIgnoreCase(let)){
				found = 1;
			}
		}
		return found;
	}
	/**
	* Takes letter guessed and searches wordArray for that letter.
	* If that letter is found, the index where it is found is compared to the 
	* guessed array and replaces the dash in that index 
	*/
	public static void correctLetter(){
		for (int z = 0; z < wordArray.length; z++){
			if (letter.compareTo(wordArray[z])==0){
				guessed[z] = wordArray[z];
			}
		}
	}
	/**
	* prints guessed array for the current word in the array
	*/
	public static void currentWord(){
		for (int i = 0; i < guessed.length;i++){
			System.out.print(guessed[i]);
		}
	}
	/**
	* Looks if ArrayList is empty and if it's not the contents are printed out.
	* @param s ArrayList that contains previous wrong guesses
	*/
	public static void printGuess(ArrayList<String> s){
		if (!s.isEmpty()){
			for (int i=0;i < s.size();i++){
				System.out.print(s.get(i)+", ");
			}
		}
		else System.out.print("");
	}
	/**
	* EXTRA CREDIT
	* determines difficulty of word using hashmap and array of letters in word
	* @param s Array that contains letters in word
	* @return diff Double that represents difficulty of word
	*/
	public static double difficulty(String[] s){
		HashMap<String, Double> wordDiff = new HashMap<>();
		
		// values of letters
		wordDiff.put("a", 8.167);
		wordDiff.put("b", 1.492);
		wordDiff.put("c", 2.782);
		wordDiff.put("d", 4.253);
		wordDiff.put("e", 12.702);
		wordDiff.put("f", 2.228);
		wordDiff.put("g", 2.015);
		wordDiff.put("h", 6.094);
		wordDiff.put("i", 6.966);
		wordDiff.put("j", 0.153);
		wordDiff.put("k", 0.772);
		wordDiff.put("l", 4.025);
		wordDiff.put("m", 2.406);
		wordDiff.put("n", 6.749);
		wordDiff.put("o", 7.507);
		wordDiff.put("p", 1.929);
		wordDiff.put("q", 0.095);
		wordDiff.put("r", 5.987);
		wordDiff.put("s", 6.327);
		wordDiff.put("t", 9.056);
		wordDiff.put("u", 2.758);
		wordDiff.put("v", 0.978);
		wordDiff.put("w", 2.360);
		wordDiff.put("x", 0.150);
		wordDiff.put("y", 1.974);
		wordDiff.put("z", 0.074);
		wordDiff.put(" ", 0.0);
		double avg=0;
		int divide = 0;
		for (int i=0; i<s.length; i++){
			avg+=wordDiff.get(s[i]);
			divide++;
		}
		double diff =  avg/divide;
		return diff;
	}
	
	
	
}