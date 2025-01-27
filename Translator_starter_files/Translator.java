// ECE 422C Translator 
// <Your Name Here>
// <Your EID Here>
// Slip days used: 0
// Fall 2024 

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
		String outputString = new String(inputString); // makes a copy of inputString. 
		return outputString;
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
