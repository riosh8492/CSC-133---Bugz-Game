// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/** Class HelpCommand extends Command.
 *  Purpose is mainly to execute Help
 *  method to show instructions of 
 *  how to play game.
 *  Reacts when button is pressed.*/

public class HelpCommand extends Command
{
	private GameWorld localGameWorld;
	
	// Record command name and GameWorld
	public HelpCommand(String command, GameWorld gw) {
		super(command);
		localGameWorld = gw;
	}
	
	// Activate help display with useful information when button/label pressed. 
	public void actionPerformed(ActionEvent evt)
	{		
		Command confirmDesc = new Command("Confirm");
		Command respondDes; 
		String helpInformation = new String("Command Keys ShortCut." + 
				"\n'a' = To accelerate the Ant."     + "\n'b' = To brake the Ant." + 
				"\n'l' = To Left turn the Ant."      + "\n'r' = To Right turn the Ant." + 
				"\n'g' = To Collide Spider and Ant." + "\n't' = To Tick the GameWorld Clock.");
		
		System.out.println("---> Help Command Invoked"); // Testing Purposes. 

		// Display help dialog box.
		respondDes = Dialog.show("Help Information", helpInformation, confirmDesc);
		
		if (respondDes == confirmDesc)  // Confirm that user exits help dialog.
		{
			System.out.println("Exited Help.");
		}
	}

}
