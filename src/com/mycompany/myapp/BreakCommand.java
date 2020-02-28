// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/** Class BreakCommand extends Command.
 *  Purpose is mainly to execute Break
 *  method of ant player in GameWorld.
 *  Reacts when button is pressed.*/

public class BreakCommand extends Command
{
	private GameWorld localGameWorld;     // Save local instance of GameWorld
	
	public BreakCommand(String command, GameWorld gw) {
		super(command);                                // Set command name 
		localGameWorld = gw;						   // Save GameWorld
	}
	
	// Calls on GameWorld Command Break when button pressed.
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("---> Break Pressed.");
		localGameWorld.brakeAnt();
	}

}
