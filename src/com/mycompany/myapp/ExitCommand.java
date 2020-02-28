// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/** Class ExitCommand extends Command.
 *  Purpose is mainly to execute Exit
 *  of Game. Gives a display to give to user.*/

public class ExitCommand extends Command 
{
	private GameWorld localGameWorld;

	// Record command name and GameWorld instance
	public ExitCommand(String command, GameWorld gw) 
	{
		super(command);		
		localGameWorld = gw;
	}
	
	// React and activate Exit command.
	public void actionPerformed(ActionEvent evt)
	{
		System.out.println("Exit Command Hit..");
		
		Command confirmDesc = new Command("Confirm"); 		      // Confirm to Exit
		Command cancelDesc = new Command("Cancel");               // Cancel to not Exit
		Command[] cmds = new Command[]{confirmDesc, cancelDesc};  // List of commands. 
		
		// Display option table to user.
		Command respondDesc = Dialog.show("Exit Game Option", "Confirm Exit Current Game Session?", cmds);
		
		// Check if user wants to EXIT.
		if (respondDesc == confirmDesc)
		{
			System.out.println("Game Exited: Game Over. \nTill next time....");
			localGameWorld.promptExit();  // Set GameWorld to Exit
			localGameWorld.exit();        // Exit Program.

		}
		else if (respondDesc == cancelDesc)
		{
			System.out.println("Cancel Exiting Game Confirmed.");
		}
	}
	

}
