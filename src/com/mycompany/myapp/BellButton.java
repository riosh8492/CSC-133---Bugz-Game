// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.plaf.Border;

/** Class BellButton extends from Button. 
 *  This class is mainly used for changing color of button, 
 *  setting padding, and primary holder of commands*/

public class BellButton extends Button
{ 
	private String commandDesc;        // Command Holder variable
	
	// Initialize BellButton creation.  
	public BellButton(String inputCom) 
	{
		commandDesc = inputCom;     // Record Command
		super.setText(commandDesc); // Set Button command.
		
		/* Set the Style of Button. */
		this.getUnselectedStyle().setBgTransparency(200);
		this.getUnselectedStyle().setBgColor(ColorUtil.rgb(161, 56, 188));
		this.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		this.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.BLACK));
				
		this.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.BLACK));
		this.getAllStyles().setPadding(Component.TOP, 10);
		this.getAllStyles().setPadding(Component.BOTTOM, 10);
		this.getAllStyles().setPadding(Component.LEFT, 15);
		this.getAllStyles().setPadding(Component.RIGHT, 15);
	}
	
	// Change the Color of Buttons that are Disabled. 
	public void disableButtonView()
	{
		this.getDisabledStyle().setBgTransparency(200);
		this.getDisabledStyle().setBgColor(ColorUtil.rgb(6, 182, 164));
		this.getDisabledStyle().setFgColor(ColorUtil.rgb(227, 27, 27));
	}
	
	public void enableButtonView()
	{
		this.getUnselectedStyle().setBgTransparency(200);
		this.getUnselectedStyle().setBgColor(ColorUtil.rgb(161, 56, 188));
		this.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
	}
	
	public void updateBellButton()
	{
		String localDesc = this.getCommand().getCommandName(); // Get current name of Command.
		super.setText(localDesc); // Set Button command.
	}
}
