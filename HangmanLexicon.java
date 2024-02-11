/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import acmx.export.java.util.ArrayList;
import acmx.export.java.util.List;

public class HangmanLexicon {
	private String fileName = "HangmanLexicon.txt";
	private List _lexicon;
	public HangmanLexicon()
	{
		_lexicon = new ArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) 
        {
           String line;
           while ((line = reader.readLine()) != null) 
           {
             _lexicon.add(line);
           }
        } 
        catch (Exception ex) 
        {
           System.out.println(ex.getMessage());
        }
	}
	
/** Returns the number of words in the lexicon. */
	public int getWordCount()
	{
		return _lexicon.size();
	}

/** Returns the word at the specified index. 
 * @throws Exception */
	public String getWord(int index) throws Exception 
	{
      return (String) _lexicon.get(index);
	}
}