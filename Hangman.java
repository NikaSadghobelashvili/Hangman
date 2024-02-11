/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {
	private HangmanCanvas canvas;
	private HangmanLexicon lexicon = new HangmanLexicon();
	private String secretWord;
	private String displayedWord;
	private RandomGenerator generator = new RandomGenerator();
	private String userInput;
	private int tries;
	private boolean hintAvailable;
	private boolean playGameOverMusic;
	private java.applet.AudioClip hintSound = MediaTools.loadAudioClip("hint.au"); //https://www.myinstants.com/en/search/?name=SONIC (imported sounds from there)
	private java.applet.AudioClip gameOver = MediaTools.loadAudioClip("gameOver.au");
	
	public void init() 
	{ 
		canvas = new HangmanCanvas(); 
		add(canvas);
	} 
    public void run() 
    {
       println("Welcome to Hangman!");
       gameProcess();
	}
    public void gameProcess()
    {
    	while(true)
    	{
    	gameStart();
    	canvas.displayWord(displayedWord,hintAvailable);
    	while(gameIsGoing(tries,displayedWord))
    	{
    	userInput = readLine();
    	checkUserInput(userInput);
    	canvas.displayWord(displayedWord,hintAvailable);
    	}
    	if(afterGame())
    	{
    		clearConsole(); //prints 50 empty lines to give console appearance of cleared Console
    		clearConsole();
    		canvas.reset();
    		println();
    	}
    	else
    	{
    		System.exit(0);
    	}
    	}
    }
    
    public void gameStart()
    {
    	playGameOverMusic=true;
    	hintAvailable=true;
    	tries = 8;
    	displayedWord="";
    	try 
    	{
			secretWord = lexicon.getWord(generator.nextInt(lexicon.getWordCount()));
		} 
    	catch (Exception ex) 
    	{
			System.out.println(ex.getMessage());
		}
    	for(int i =0; i<secretWord.length();i++)
    	{
    		displayedWord+="-";
    	}
    	println("You have "+ tries +" tries left, Good Luck!");
    	println("The word looks like this: " + displayedWord);
    }
    
    
    public void checkUserInput(String input)
    {
    	if(input.toUpperCase().equals(secretWord))
    	{
    		displayedWord = input.toUpperCase();
    		println("You guessed the word: " + secretWord);
    	}
    	else if(input.toUpperCase().equals("HINT") && hintAvailable)
    	{
    		hintSound.play();
    		hintAvailable=false;
    		for(int i=0;i<displayedWord.length();i++)
    		{
    			if(displayedWord.charAt(i)=='-')
    			{
    				checkUserInput(Character.toString(secretWord.charAt(i)));
    				break;
    			}
    		}
    	}
    	else if(input.toUpperCase().equals("HINT") && !hintAvailable)
    	{
    		println("You've already used hint");
    		println("Tries left: " + tries);
      	    println("The word looks like this: " + displayedWord);
    	}
    	else if(input.length()==secretWord.length())
    	{
    		println("Your guess was wrong");
    		tries--;
    		canvas.noteIncorrectGuess(input);
    		println("Tries left: " + tries);
      	    println("The word looks like this: " + displayedWord);
    	}
    	else if (input.length()>1 || input.length()==0)
    	{
    		println("Incorrect input form");
    		println("Tries left: " + tries);
      	    println("The word looks like this: " + displayedWord);
    	}
    	else if(input.length()==1 && !isLetter(input))
    	{
    		println("Incorrect input form, enter only letters");
    		println("Tries left: " + tries);
      	    println("The word looks like this: " + displayedWord);
    	}
    	else if(input.length()==1 && secretWord.contains(input.toUpperCase()) && !displayedWord.contains(input.toUpperCase()))
    	{
    		println("Your guess was correct");
    		for(int i =0; i<secretWord.length();i++)
    		{
    			if(input.toUpperCase().charAt(0)== secretWord.charAt(i))
    			{
    				replaceAt(i,secretWord.charAt(i));
    			}
    		}
    	   println("Tries left: " + tries);
    	   if(!displayedWord.equals(secretWord))
     	   println("The word looks like this: " + displayedWord);
    		
    	}
    	else if(!secretWord.contains(input.toUpperCase()))
    	{
    		tries--;
    		println("Your guess was wrong");
    		canvas.noteIncorrectGuess(input);
    		println("Tries left: " + tries);
    		if(tries!=0)
    		println("The word looks like this: " + displayedWord);
    	}
    }
    
    public boolean gameIsGoing(int tries, String displayedWord)
    {
    	if(tries==0 && displayedWord!=secretWord)
    	{
    		println("The word was: " + secretWord);
    		println("You're Completely Hung");
    		println("You Lose");
    		return false;
    	}
    	else if(displayedWord.equals(secretWord))
    	{
    		println("You Win, Congratulations!");
    		return false;
    	}
    	return true;
    }
    
    
    public void replaceAt(int index, char c)
    {
    	String temp= "";
    	for(int i =0; i< displayedWord.length();i++)
    	{
    		if(i!=index)
    		{
    			temp+=displayedWord.charAt(i);
    		}
    		else
    		{
    			temp+=c;
    		}
    	}
    	displayedWord=temp;
    }
    
    
    public boolean isLetter(String input)
    {
    	char ch = input.charAt(0);
    	if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
    	{
    		return true;
    	}
    	return false;
    }
    
    
    public boolean afterGame()
    {
    	boolean continueGame;
    	println();
    	println("Would you like to continue the game?");
    	while(true)
    	{
    		if(playGameOverMusic)
    		{
    			gameOver.play();
    			playGameOverMusic=false;
    		}
    	
    	String answer = readLine();
    	if(answer.equalsIgnoreCase("no"))
    	{
    		continueGame=false;
    		break;
    	}
    	else if(answer.equalsIgnoreCase("yes"))
    	{
    		continueGame=true;
    		gameOver.stop();
    		break;
    	}
    	else
    	{
    		println("Please Enter only yes or no");
    	}	
    	}
    	return continueGame;
    }
    
    
    public void clearConsole()
    {
    	for(int i =0;i<50;i++)
    	{
    		println();
    	}
    }
}
