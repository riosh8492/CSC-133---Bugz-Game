// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/** Class LEFTCommand extends Command.
 *  Purpose is mainly to execute LEFT
 *  method of ant player in GameWorld.
 *  Reacts when button is pressed.*/

public class LeftCommand extends Command
{
	private GameWorld localGameWorld;
	
	// Record Command name and GameWorld
	public LeftCommand(String command, GameWorld gw) 
	{
		super(command);
		localGameWorld = gw;
	}
	
	// Calls on GameWorld Command Left and displays it to user.
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("---> Left Pressed.");
		localGameWorld.redirectAnt('L');
	}
}
