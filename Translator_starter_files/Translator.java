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
	public String translate (String inputString) 
	{ 
		// modify the following code. Add/delete anything you want after this point.
		StringBuilder outputString = new StringBuilder(); // StringBuilder allows for effective string manipulation
		
		String[] words = inputString.split("\\s+"); // Splitting the string into words (/s+ matches multiple whitespace)
		
		for (int i = 0; i < words.length; i++){
			String word = words[i];
			// Adding the altered word back into string (.append adds content to the end of an existing sequence of characters)
			outputString.append(alteredWord(word)).append(" "); 
		}
		return outputString.toString().trim(); // Returns full translation
	}

	private String alteredWord(String word){
		char firstLetter = word.charAt(0);
		if (Vowel(firstLetter)){ //checks if the first letter is a vowel
			return word + "yay";
		}
		return word; //place holder
	}
	private boolean Vowel(char c){ //Identity of a vowel
		String vowels = "aeiouAEIOU";
		return vowels.indexOf(c) >= 0;
	}
	public static void main (String args[]) 
	{ 
		if (args.length != 1) 
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
    processLinesInFile (args[0]);
	}
}
