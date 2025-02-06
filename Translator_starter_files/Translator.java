// ECE 422C Pig Latin Translator 
// Yonatan Teshome
// YH23572
// Slip days used: 0
// Spring 2025 


/**
 * The Translator class provides methods to convert English text into
 * Pig Latin while preserving spacing and handling special rules such
 * as hyphenation, contractions, and skipping words with digits or
 * special symbols.
 *
 * User Instruction: Compile and run the Translator with a single
 * command-line argument specifying a file of english phrases to translate
 *
 * @author
 * 			Yonatan Teshome
 * 			ECE422C, Spring 2025
 * @version 1.0 (January 28, 2025)
 *
 * @see file:///C:/Users/Yonatan%20Teshome/Desktop/ECE%20422c/Lab%201/Translator_starter_files/Translator.html
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator
{
	/**
	 * Reads each line form the specified file, prints the original line,
	 * converts it to Pig latin, and prints the translation.
	 * @parm filename the path to the file containing lines to translate
	 *
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
			//FileReader freader = new FileReader(filename);
			FileReader freader = new FileReader("Translator_starter_files/a1-piglatin-phrases.txt");

			BufferedReader reader = new BufferedReader(freader);

			for (String s = reader.readLine(); s != null; s = reader.readLine())
			{
				System.out.println("The input string is: " + s);
				String pigLatin = translator.spacePreserver(s); //edited hopefully allowed
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
	 *
	 * Takes a single line of text and returns a version with each clump
	 * translated to Pig Latin if eligible, preserving all original spacing.
	 *
	 * @param inputString the original line of text
	 * @return the Pig Latin translated line, keeping its spacing
	 */
	public String spacePreserver(String inputString) {
		if (inputString == null || inputString.isEmpty()) {
			return "";
		}

		//Regex approach (clumps up white spaces or non-white spaces)
		Pattern pattern = Pattern.compile("\\s+|[^\\s]+");
		Matcher matcher = pattern.matcher(inputString);

		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String clump = matcher.group(); // produce the exact substring

			if (clump.matches("\\s+")) {
				sb.append(clump); //append the whitespace
			} else {
				String pigLatin = translateClump(clump);// start translating
				sb.append(pigLatin);
			}
		}
		return sb.toString();
	}

	/**
	 * Translates one non-whitespace clump to Pig Latin if it contains only
	 * letter characters (also apostrophes). If it contains digits or other
	 * special symbols, it is returned without alterations (Rule 6)
 	 * @param clump a non-whitespace substring
	 * @return the Pig Latin version of the clump, or unchanged if skipped
	 */
	public String translateClump (String clump) {

		if (weirdSymbols(clump)) {
			return clump; // Rule 6
		}

		//Trailing Punctuation
		int lastIndex = clump.length() - 1; //Extract punctuation from the end of the word
		while (lastIndex >= 0 && !Character.isLetterOrDigit(clump.charAt(lastIndex))) {
			lastIndex--;
		}
		String allWord = clump.substring(0, lastIndex + 1); //only words remain
		String punctuation = clump.substring(lastIndex + 1); // remaining is a trailing punctuation

		// Hyphens
		if (allWord.contains("-")) {
			return hyphenatedWord(allWord) + punctuation;
		}
		return singularWord(allWord) + punctuation;
	}

	/**
	 * Applies standard Pig Latin rules to a single "word" of letters
	 * and apostrophes:
	 * * If it begins with a vowel, appends "yay"
	 * * If it has a vowel later, move the leading consonants to the end + "ay"
	 * * if it has no vowel, just append "ay"
	 *
	 * @param allWord a clump composed of purely alphabetic/ apostrophe word
	 * @return the Pig Latin translation of the word
	 */
	private String singularWord(String allWord){
		if (allWord.isEmpty() || !allWord.matches("[a-zA-Z']+")) {
			return allWord; // returns if empty (not possible tbh) or if it's not letters/conjuction
		}

		int firstVowel = firstVowelIndex(allWord); //finds placement of the first vowel in word
		if (firstVowel == 0) { //checks if the first letter is a vowel
			return allWord + "yay";
		}else if(firstVowel > 0) {
			String first = allWord.substring(0, firstVowel); // storing the consonates that are in front of the vowel
			String last = allWord.substring(firstVowel); // storing the rest of the world (prep for shifting)
			return last + first + "ay"; //reorder
		}else {
				return allWord + "ay";
		}
	}

	/**
	 * Splits a hyphenated clump into separate ones, and applies Pig Latin to
	 * each clump (if applicable), then reassembles them with a hyphen
	 * @param word the hyphenated string to split and translate
	 * @return a single string of Pig Latin-transformed clump joined by "-"
	 */
	private String hyphenatedWord(String word) {
		String[] parts = word.split("-");
		StringBuilder rebuild = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			// If chunk has digits/weird symbols => skip transform
			if (weirdSymbols(parts[i])) {
				rebuild.append(parts[i]);
			} else {
				// Otherwise, apply 'singularWord' to that piece
				rebuild.append(singularWord(parts[i]));
			}
			if (i < parts.length - 1) {
				rebuild.append("-");
			}
		}
		return rebuild.toString();
	}

	/**
	 * Locates the index of the first vowel in a word (treats 'y' as
	 * a vowel only if it is not the first letter)
	 *
	 * @param allWord the string to inspect
	 * @return the index of the first vowel, or -1 if no vowel is found
	 */
	private int firstVowelIndex(String allWord){
		for (int i = 0; i < allWord.length(); i++){
			char c = Character.toLowerCase(allWord.charAt(i));
			if ("aeiou".indexOf(c) >= 0 || (c == 'y' && i > 0)){ //checks if the char is a vowel (also y edge case)
				return i;
			}
		}
		return -1; //no vowel
	}


	/**
	 * Determines whether a given clump contains digits or disallowed symbols,
	 * which means it should remain unchanged(Rule 6)
	 *
	 * @param clump the clump to check
	 * @return true if the clump has digits or forbidden symbols: false otherwise
	 */
	private boolean weirdSymbols(String clump) {
		if (clump.matches(".*\\d+.*")){
			return true; //numbers
		}
		if (clump.matches(".*[@#$%^&*_=+<>`~].*")) {
			return true; //other symbols that lead to skipping
		}
		return false;
	}

	/**
	 * The main entry point. Expects exactly one argument: the filename
	 * to be processed. Exits if the argument count is wrong.
	 *
	 * @param args command-line arguments
	 */
	public static void main (String args[]) {
		if (args.length != 1){
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		processLinesInFile (args[0]);
	}
}
