// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.charts.models.Point;

/** Class FixedObject inheriting from Class GameObject is meant 
 *  to hold attributes, and methods for Objects that are Fixed
 *  in their location. It also serves to be parent to more specific
 *  things like Flags, and FoodStations. 
 *  */

public abstract class FixedObject extends GameObject
{	
	//This sets the values for FixedObjects to GameObject.
	public FixedObject(Point inputLocation, int inputColor, int inputSize) 
	{
		super(inputLocation, inputColor, inputSize); // Constructor
	}
}
