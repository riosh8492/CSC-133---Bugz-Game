// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/** Class ClockCommand extends Command.
 *  Purpose is mainly to execute Tick
 *  method of GameWorld. Reacts when button 
 *  is pressed.*/

/*   DEPRECIATED   */

public class ClockCommand extends Command
{
	private GameWorld localGameWorld;      // Save local instance of GameWorld.
	
	public ClockCommand(String command, GameWorld gw)
	{
		super(command);         // Set Command name
		localGameWorld = gw;    // Save GameWorld 
	}
	
	// Calls on GameWorld Command Tick when button pressed.
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("---> Tick Pressed.");
		//localGameWorld.clockTicker();
	}

}