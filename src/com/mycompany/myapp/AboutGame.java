// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/** Class AboutGame extends from Command.
 *  This class is mainly to give information
 *  to the user via About command button*/

public class AboutGame extends Command 
{	
	// Set the name of the Command,
	public AboutGame()
	{
		super("About");
	}
	
	// Set actionPerformed to react when pressed and display info.
	public void actionPerformed(ActionEvent evt)
	{
		Command confirmDesc = new Command("Confirm");  // Confirm button to exit display.
		Command respondDes;                            // Record Command to hold output.
		
		// Display to show about information.
		respondDes = Dialog.show("About", "Author: Hector Rios.\nCourse: CSC-133. \nVer.2.17", confirmDesc);
		
		// Determine user input pressing.
		if (respondDes == confirmDesc)
		{
			System.out.println("Exited About Menu Information.");
		}
	}

}
