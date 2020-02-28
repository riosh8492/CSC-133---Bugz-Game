// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;

/**Class EasternContainer extends Container.
 * Class is meant to create custom container 
 * style / look / layout
 * */

public class EasternContainer extends Container {

	// Set style of container. 
	public EasternContainer()
	{
		this.setLayout( new BoxLayout( BoxLayout.Y_AXIS ) );
		this.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLACK));
		this.getAllStyles().setPadding(Component.TOP, 40);
		this.getAllStyles().setPadding(Component.BOTTOM, 50);
	}
}
