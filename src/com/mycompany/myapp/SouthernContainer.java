// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

/**Class Southern Container extends Container.
 * Class is meant to create custom container 
 * style / look / layout
 * */

public class SouthernContainer extends Container {
	public SouthernContainer()
	{
		//this.getAllStyles.setBorder(Border.createLineBorder(2, ColorUtil.BLUE));
		this.setLayout( new FlowLayout(Component.CENTER) );
		this.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLACK));
		
		//this.getUnselectedStyle().setBorder(Border.createLineBorder(2,ColorUtil.BLACK));
		this.getAllStyles().setPadding(Component.LEFT, 10);
		this.getAllStyles().setPadding(Component.RIGHT, 10);
				
	}

}
