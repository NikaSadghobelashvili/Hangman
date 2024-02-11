/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;
import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	
	private int tries =8;
	private int _height = 471;
	private int _width=376;
	private String incorrectLettersTxt="";
	private GLabel incorrectLettersLbl = new GLabel(incorrectLettersTxt,30,420);
	private GLabel secretWordLbl = new GLabel("",30,400);
	private GLine scaffold = new GLine(_width/4,_height*0.7,_width/4,_height*0.7-SCAFFOLD_HEIGHT);
	private GLine beam = new GLine(_width/4,_height*0.7-SCAFFOLD_HEIGHT,_width/4+BEAM_LENGTH,_height*0.7-SCAFFOLD_HEIGHT);
	private GLine rope = new GLine(_width/4+BEAM_LENGTH,_height*0.7-SCAFFOLD_HEIGHT,_width/4+BEAM_LENGTH,_height*0.7-SCAFFOLD_HEIGHT+ROPE_LENGTH);
	private GOval head = new GOval(rope.getX()-HEAD_RADIUS,rope.getY()+ROPE_LENGTH,2*HEAD_RADIUS,2*HEAD_RADIUS);
	private GLine body = new GLine(head.getX()+HEAD_RADIUS,head.getY()+2*HEAD_RADIUS,head.getX()+HEAD_RADIUS,head.getY()+2*HEAD_RADIUS+BODY_LENGTH);
	private GLine leftArm = new GLine(body.getX(),body.getY()+ARM_OFFSET_FROM_HEAD,body.getX()-ARM_LENGTH,body.getY()+ARM_OFFSET_FROM_HEAD+ARM_LENGTH*2);
	private GLine rightArm = new GLine(body.getX(),body.getY()+ARM_OFFSET_FROM_HEAD,body.getX()+ARM_LENGTH,body.getY()+ARM_OFFSET_FROM_HEAD+ARM_LENGTH*2);
	private GLine leftLeg = new GLine(body.getX(),body.getY()+BODY_LENGTH,body.getX()-LEG_LENGTH*0.6,body.getY()+BODY_LENGTH+LEG_LENGTH*3);
	private GLine rightLeg = new GLine(body.getX(),body.getY()+BODY_LENGTH,body.getX()+LEG_LENGTH*0.6,body.getY()+BODY_LENGTH+LEG_LENGTH*3);
	private GLine leftEye1 = new GLine(head.getX()+ EYE_OFFSET_FROM_HEAD, head.getY()+EYE_OFFSET_FROM_HEAD,head.getX()+ EYE_OFFSET_FROM_HEAD+EYE_LENGTH, head.getY()+EYE_OFFSET_FROM_HEAD+EYE_LENGTH);
	private GLine leftEye2 = new GLine(head.getX()+ EYE_OFFSET_FROM_HEAD+EYE_LENGTH, head.getY()+EYE_OFFSET_FROM_HEAD,head.getX()+ EYE_OFFSET_FROM_HEAD+EYE_LENGTH-EYE_LENGTH, head.getY()+EYE_OFFSET_FROM_HEAD+EYE_LENGTH);
	private GLine rightEye1 = new GLine(head.getX()+ EYE_OFFSET_FROM_HEAD+DISTANCE_BETWEEN_EYES, head.getY()+EYE_OFFSET_FROM_HEAD,head.getX()+ EYE_OFFSET_FROM_HEAD+EYE_LENGTH+DISTANCE_BETWEEN_EYES, head.getY()+EYE_OFFSET_FROM_HEAD+EYE_LENGTH);
	private GLine rightEye2 = new GLine(head.getX()+ EYE_OFFSET_FROM_HEAD+EYE_LENGTH+DISTANCE_BETWEEN_EYES, head.getY()+EYE_OFFSET_FROM_HEAD,head.getX()+ EYE_OFFSET_FROM_HEAD+EYE_LENGTH-EYE_LENGTH+DISTANCE_BETWEEN_EYES, head.getY()+EYE_OFFSET_FROM_HEAD+EYE_LENGTH);
	private GLabel triesLbl = new GLabel(Integer.toString(tries),_width-30,50);
	private GLine deadLeftArm = new GLine(body.getX(),body.getY()+ARM_OFFSET_FROM_HEAD,body.getX()-ARM_LENGTH*0.3,body.getY()+ARM_OFFSET_FROM_HEAD+ARM_LENGTH*2.5);
	private GLine deadRightArm = new GLine(body.getX(),body.getY()+ARM_OFFSET_FROM_HEAD,body.getX()+ARM_LENGTH*0.3,body.getY()+ARM_OFFSET_FROM_HEAD+ARM_LENGTH*2.5);
	private GLine deadLeftLeg = new GLine(body.getX(),body.getY()+BODY_LENGTH,body.getX()-LEG_LENGTH*0.2,body.getY()+BODY_LENGTH+LEG_LENGTH*3.4);
	private GLine deadRightLeg = new GLine(body.getX(),body.getY()+BODY_LENGTH,body.getX()+LEG_LENGTH*0.2,body.getY()+BODY_LENGTH+LEG_LENGTH*3.4);
	private GRect hintBox = new GRect(315,430,50,30);
	private GLabel hintLbl = new GLabel("HINT",327,450);
	private GLabel hintInfoLbl = new GLabel("type 'HINT' for a hint",270,420);
	public HangmanCanvas()
	{
		add(scaffold);
		add(beam);
		add(rope);
		add(incorrectLettersLbl);
		add(secretWordLbl);
		hintBox.setFilled(true);
		hintBox.setFillColor(Color.YELLOW);
		add(hintBox);
		add(hintLbl);
		hintInfoLbl.setFont("BOLD-"+10);
		add(hintInfoLbl);
		triesLbl.setFont("BOLD-"+35);
		triesLbl.setColor(Color.GREEN);
		add(triesLbl);
	}
	
/** Resets the display so that only the scaffold appears */
	public void reset() 
	{
		removeHangman();
		resetLabels();
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word, boolean hintAvailable) 
	{
		secretWordLbl.setFont("BOLD-"+35);
		secretWordLbl.setLabel(word);
		if(!hintAvailable)
		changeColorOfHintBox();
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(String letter) 
	{
		if(!incorrectLettersLbl.getLabel().contains(letter) && letter.length()==1)
		{
			incorrectLettersTxt+=letter.toLowerCase();
			incorrectLettersLbl.setLabel(incorrectLettersTxt);
		}
		addHangmanObject(tries);
		tries--;
		triesLbl.setLabel(Integer.toString(tries));
		changeColorOfTriesLbl(tries);
		
	}
	private void changeColorOfHintBox()
	{
		hintBox.setFillColor(Color.LIGHT_GRAY);
	}
	private void changeColorOfTriesLbl(int tries)
	{
		if(tries>=6)
		{
			triesLbl.setColor(Color.GREEN);
		}
		else if(tries>=3)
		{
			triesLbl.setColor(Color.YELLOW);
		}
		else
		{
			triesLbl.setColor(Color.RED);
		}
	}
	
	private void addHangmanObject(int tries)
	{
		switch (tries) {
	    case 1:
	        addDeadEyes();
	        addDeadHangman();
	        hideHintSystem();
	        break;
	    case 2:
	        addClosedEyes();
	        break;
	    case 3:
	        addObject(rightLeg);
	        break;
	    case 4:
	        addObject(leftLeg);
	        break;
	    case 5:
	        addObject(rightArm);
	        break;
	    case 6:
	        addObject(leftArm);
	        break;
	    case 7:
	        addObject(body);
	        break;
	    case 8:
	        addObject(head);
	        break;
	}
	}
	private void addObject(GObject object)
	{
		add(object);
		double y = object.getLocation().getY();
		object.setLocation(object.getLocation().getX(),object.getLocation().getY()-400);
		while(object.getLocation().getY()!=y)
		{
			try 
			{
				Thread.sleep(10);
			} catch (InterruptedException e) 
			{
				System.out.println(e.getMessage());
			}
			object.move(0,10);
		}
	}
	private void hideHintSystem()
	{
		hintBox.setVisible(false);
		hintLbl.setVisible(false);
		hintInfoLbl.setVisible(false);
	}
	private void showHintSystem()
	{
		hintBox.setVisible(true);
		hintLbl.setVisible(true);
		hintInfoLbl.setVisible(true);
	}
	private void addDeadHangman()
	{
		remove(leftArm);
		remove(rightArm);
		remove(leftLeg);
		remove(rightLeg);
		add(deadLeftArm);
		add(deadRightArm);
		add(deadLeftLeg);
		add(deadRightLeg);
	}
	
	private void addClosedEyes()
	{
		add(leftEye1);
		add(rightEye1);
	}
	private void addDeadEyes()
	{
		add(leftEye2);
		add(rightEye2);
		head.setFilled(true);
		head.setFillColor(Color.RED);
	}
	private void removeClosedEyes()
	{
		remove(leftEye1);
		remove(rightEye1);
	}
	private void removeDeadEyes()
	{
		remove(leftEye2);
		remove(rightEye2);
	}
	private void removeHangman()
	{
		head.setFillColor(Color.white);
		remove(head);
		remove(body);
		remove(leftArm);
		remove(rightArm);
		remove(leftLeg);
		remove(rightLeg);
		remove(deadLeftArm);
		remove(deadRightArm);
		remove(deadLeftLeg);
		remove(deadRightLeg);
		removeClosedEyes();
		removeDeadEyes();
	}
	private void resetLabels()
	{
		tries=8;
		triesLbl.setColor(Color.GREEN);
		triesLbl.setLabel(Integer.toString(tries));
		incorrectLettersTxt="";
		incorrectLettersLbl.setLabel(incorrectLettersTxt);
		secretWordLbl.setLabel("");
		hintBox.setFillColor(Color.YELLOW);
		showHintSystem();
	}

	private static final int SCAFFOLD_HEIGHT = 250;
	private static final int BEAM_LENGTH = 94;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 20;
	private static final int BODY_LENGTH = 68;
	private static final int ARM_OFFSET_FROM_HEAD = 10;
	private static final int ARM_LENGTH = 30;
	private static final int LEG_LENGTH = 30;
	private static final int EYE_OFFSET_FROM_HEAD = 10;
	private static final int EYE_LENGTH = 6;
	private static final int DISTANCE_BETWEEN_EYES=16;

}
