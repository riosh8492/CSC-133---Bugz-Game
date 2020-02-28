// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import com.codename1.charts.models.Point;


/** Class MovableObject extends from GameObject, and serves to be parent class
 *  to all things that can move in the game. It serves to hold methods, and 
 *  attributes related to movement. 
 */

public abstract class MovableObject extends GameObject
{
	private int heading;      // Hold to heading of the Movable Object.
	private int speed;        // Hold to speed of the Movable Object.
	
	// Constructor meant to record input to have location, color, size, heading, and speed.
	public MovableObject(Point inputLocation, int inputColor, int inputSize, int iHeading, int iSpeed) 
	{
		super(inputLocation, inputColor, inputSize);		
		heading = iHeading;     // Set heading variable to input given.
		speed = iSpeed;         // Set heading variable to input given.
	} 
	
	// Get heading info of object.
	public int obtainHeading()
	{
		return heading;
	}
	
	//Get speed info of object.
	public int obtainSpeed()
	{
		return speed;
	}
	
	//Set heading info of object.
	public void setHeading(int input)
	{
		heading = input;  // (Note to Self to place Limiter to input amount.)
	}
	
	//Set speed info of object.
	public void setSpeed(int input)
	{
		speed = input;
	}
	
	//Updates gameObject Location based on current heading and speed.
	public void move(int tickRate)
	{
		// Computation of new location based on Heading / Speed.
		Point oldLocation = obtainLocation();        // Get the current Location.
		Point newLocation = new Point();             // Final new location variable.
		
		double theta = Math.toRadians(90 - heading); // Change the degree result to Radians.
		double deltaX = Math.cos(theta) * speed;     // Find change in X axis
		double deltaY = Math.sin(theta) * speed;     // Find change in y axis
		
		//ADD old and new changes from location(s). 
		double newX = oldLocation.getX() + deltaX;   // Determine the change in X.
		double newY = oldLocation.getY() + deltaY;   // Determine the change in Y.
		
		//This sets the new values to new location Point.
		newLocation.setX((float) newX); 
		newLocation.setY((float) newY);
		
		setLocation(newLocation);                    // Set current location to updated Point location.
	}
	
}
