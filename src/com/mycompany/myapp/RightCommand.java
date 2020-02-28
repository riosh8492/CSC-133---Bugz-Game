// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/** Class RightCommand extends Command.
 *  Purpose is mainly to execute Right
 *  method of ant player in GameWorld.
 *  Reacts when button is pressed.*/

public class RightCommand extends Command
{
	private GameWorld localGameWorld;
	
	// Record Command name and GameWorld
	public RightCommand(String command, GameWorld gw) {
		super(command);
		localGameWorld = gw;
	}
	
	// Calls on GameWorld Command Right when button/key pressed.
	public void actionPerformed(ActionEvent e)
	{	
		System.out.println("---> Right Pressed.");
		localGameWorld.redirectAnt('R');
	}

}