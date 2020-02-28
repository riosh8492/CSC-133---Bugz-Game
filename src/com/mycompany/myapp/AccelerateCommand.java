// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/** Class AccelerateCommand extends Command.
 *  Purpose is mainly to execute Accelerate
 *  method of ant player in GameWorld.
 *  Reacts when button is pressed.*/

public class AccelerateCommand extends Command //implements ActionListener<Command>
{
	private GameWorld localGameWorld;          // Local holder of GameWorld
	
	// Sets the command name and saves instance of GameWorld
	public AccelerateCommand(String command, GameWorld gw) {
		super(command);
		localGameWorld = gw;   // Save GameWorld
	}
		
	// Calls on GameWorld Command Accelerate when button pressed.
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("---> Accelerate Pressed.");
		localGameWorld.accerlateAnt();
	}
	
}
