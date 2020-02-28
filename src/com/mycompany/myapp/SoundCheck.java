// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

/** Class SoundCheck extends CheckBox.
 *  Purpose is to set up the style of 
 *  CheckBox, and  */

public class SoundCheck extends CheckBox implements ActionListener
{
	private GameWorld localGameWorld;
	
	// Records GameWorld and sets style of checkBox.
	public SoundCheck(GameWorld globalWorld)
	{
		super("Sound");
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBgColor(ColorUtil.CYAN);
		localGameWorld = globalWorld;
		if (localGameWorld.returnSound())
		{
			this.setSelected(true);
		}
		else
		{
			this.setSelected(false);
		}
	}
	

	// Listens to when the sound check box is flipped and updates sound boolean variable.
	@Override
	public void actionPerformed(ActionEvent evt) 
	{
		System.out.println("Sound Check Box Clicked");
		if (((CheckBox) evt.getComponent()).isSelected())
		{
			System.out.println("Setting Sound to ON");
			localGameWorld.setSoundStatus(true);
			localGameWorld.manageSounds(true);
		}
		else
		{
			System.out.println("Setting Sound to OFF");
			localGameWorld.setSoundStatus(false);
			localGameWorld.manageSounds(false);
		}
	}
}
