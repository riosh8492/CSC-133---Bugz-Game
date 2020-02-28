package com.mycompany.myapp;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

/** Interface IDrawable meant to 
 *  provide GameObject Classes with
 *  ability to produce picture images
 *  on MapView.*/

public interface IDrawable 
{
	public void draw(Graphics g, Point pCmpRelPrnt);
}
