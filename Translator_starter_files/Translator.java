// ECE 422C Pig Latin Translator 
// Yonatan Teshome
// YH23572
// Slip days used: 0
// Spring 2025 

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Translator
{


	/**
	 * Opens the file specified in String filename, reads each line in it
	 * Invokes translate () on each line in the file, and prints out the
	 * translated piglatin string.
	 * @param filename - the name of the file that needs to be read
	 */
	public static void processLinesInFile (String filename)
	{

		Translator translator = new Translator();
		try
		{
			FileReader freader = new FileReader(filename);
			BufferedReader reader = new BufferedReader(freader);

			for (String s = reader.readLine(); s != null; s = reader.readLine())
			{
				System.out.println("The input string is: " + s);
				String pigLatin = translator.translate(s);
				System.out.println("The Pig Latin version is: " + pigLatin);
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println ("Error: File not found. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
		catch (IOException e)
		{
			System.err.println ("Error: IO exception. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Converts the inputString into piglatin based on rules specified
	 * in your assignment write-up.
	 *
	 * @param inputString - the String that needs to be translated to
	 * 			piglatin
	 * @return the String object containing the piglatin translation of the
	 *         inputString
	 */
	public String translate (String inputString) {

		if (inputString == null || inputString.trim().isEmpty()) {
			return ""; // Return an empty string
		}

		StringBuilder outputString = new StringBuilder(); // StringBuilder allows for effective string manipulation

		String[] words = inputString.split("\\s+"); // Splitting the string into words (/s+ matches multiple whitespace)


		for (int i = 0; i < words.length; i++){
			String word = words[i];
			if (!word.isEmpty()) {

				// Adding the altered word back into string (.append adds content to the end of an existing sequence of characters)
				outputString.append(alteredWord(word)).append(" ");
			}
		}
		return outputString.toString().trim(); // Returns full translation
	}

	private String alteredWord(String word) {

		if (word == null || word.isEmpty()) { //checks if the word is empty so runtime error doesn't occur
			//System.out.println("Empty string (skipping processing)");
			return word;
		}

		//Trailing Punctuation
		int lastIndex = word.length() - 1; //Extract punctuation from the end of the word
		while (lastIndex >= 0 && !Character.isLetterOrDigit(word.charAt(lastIndex))) {
			lastIndex--;
		}
		String allWord = word.substring(0, lastIndex + 1);
		String punctuation = word.substring(lastIndex + 1);


		// Hyphens
		if (allWord.contains("-")) {
			return hyphenatedWord(allWord) + punctuation;
		}

		return singularWord(allWord) + punctuation;
	}

	private String hyphenatedWord(String word) {
		String[] parts = word.split("-");
		StringBuilder rebuild = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			rebuild.append(alteredWord(parts[i]));
			if (i < parts.length - 1) {
				rebuild.append("-");
			}
		}
		return rebuild.toString();
	}
	private String singularWord(String allWord){
		if (allWord.isEmpty()) {
			return allWord;
		}
		char firstLetter = allWord.charAt(0);
		if (Vowel(firstLetter)) { //checks if the first letter is a vowel
			return allWord + "yay";
		}
		int firstVowel = firstVowelIndex(allWord); //finds placement of the first vowel in word
		if(firstVowel == -1){return allWord + "ay";}

		String first = allWord.substring(0, firstVowel); // storing the consonates that are in front of the vowel
		String last = allWord.substring(firstVowel); // storing the rest of the world (prep for shifting)
		return last + first + "ay"; //reorder
	}

	private int firstVowelIndex(String allWord){
		for (int i = 0; i < allWord.length(); i++){
			if ("aeiouAEIOU".indexOf(allWord.charAt(i)) >= 0){ //checks if the char is a vowel
				return i;
			}
		}
		return -1; //no vowel
	}

	private boolean Vowel(char c){ //Identity of a vowel
		String vowels = "aeiouAEIOU";
		return vowels.indexOf(c) >= 0;
	}

	public static void main (String args[]) {
		if (args.length != 1){
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		/*
		OUTPUT OBSERVATIONS:
			1) PUNCTUATION IS STAYING IN PLACE INSTEAD OF MOVING TO THE FRONT
			2) CODE IS REPEATING EACH INPUT TWICE
				A) FIRST TIME = THROUGH ITS RESPECTIVE LOOP (CORRECT)
				B) SECOND LOOP = ALL VOWELS AND IS ADDING "YAY" TO EACH WORD
			3) NOT RECOGNISING THAT - MAKE THE WORDS SEPARATE WHILE USING - TO CONNECT THE WORDS TOGETHER.


		COURSE OF ACTION:
			1) FIGURE OUT THE LOOP ISSUE
				A) LOOP THOUGH MY CONDITIONS OF MY LOOPS TO SEE WHY IT'S DOING IT EXACTLY
				2 TIMES COMPARED TO GOING ON FOREVER, OR STOPPING AT 1 LOOP
			2) MAKE A CONDITION THAT LOOKS FOR THE PUNCTUATIONS " , (...) : ; ' . ! ?" (THE () ARE NOT PUNCTUATION)
			3) MAKE A CONDITION THAT CHECKS FOR - AND TREAT IT DIFFERENTLY FROM THE OTHER PUNCTUATION
			4) LOOK OUT FOR RULE 6 AND MAKE SURE ANY OTHER EDGE CASES NOT IN THE INPUT TEXT ARE PRESENTED FOR
		 */
		processLinesInFile (args[0]);
	}
}
