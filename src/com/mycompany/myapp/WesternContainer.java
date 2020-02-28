// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;

/**Class WesternContainer extends Container.
 * Class is meant to create custom container 
 * style / look / layout
 * */

public class WesternContainer extends Container {

	public WesternContainer()
	{
		//this.getAllStyles.setBorder(Border.createLineBorder(2, ColorUtil.BLUE));
		this.setLayout( new BoxLayout( BoxLayout.Y_AXIS ) );
		this.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLACK));
		
		this.getUnselectedStyle().setBgTransparency(100);
		this.getAllStyles().setPadding(Component.TOP, 30);
		this.getAllStyles().setPadding(Component.BOTTOM, 50);
	}	
}
