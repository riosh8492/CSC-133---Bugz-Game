// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import java.util.Vector;

import com.codename1.charts.models.Point;

/** Class GameObject serves as an abstract class that holds basic attributes of gameObjects 
 *  that will be included in other specific gameObjects. Its purpose is to be inherited from
 *  so that other classes can be made from it. It acts as the bare bones of objects in the game.
 * */

public abstract class GameObject implements IDrawable, ICollider
{
	private Point location;     // Holds location of gameObject
	private int size;           // Holds size of gameObject
	private static int color;   // Holds color of gameObject
	
	// Constructor sets Location/Size/Color to given parameters 
	public GameObject(Point inputLocation, int inputColor, int inputSize) 
	{
		location = inputLocation; // Location on game Map
		size = inputSize;         //This is set to the size of ANT and FLAG. 
		color = inputColor;       // The color the object will take on.
	}
	
	// Returns gameObject current size
	public int obtainSize()
	{
		return size;
	}
	
	// Returns gameObject current color
	public int obtainColor()
	{
		return color;
	}
	
	// Returns gameObject current location
	public Point obtainLocation()
	{
		return location;
	}
	
	// Sets the color of gameObject
	public void setColor(int shadeInput)
	{
		color = shadeInput;
	}
	
	// Sets the location of gameObject
	public void setLocation(Point recallPoint)
	{
		location = recallPoint;
	}
	
	// Add Object to list of Collided Objects.
	public abstract void addCollisionObject(GameObject id);
	
	// If the Object exists in Collection, remove it.
	public abstract void removeCollisionObject(GameObject id);
	
	//Returns true if input in Vector Collision Collection
	public abstract boolean containsCollisionObject(GameObject id);

	public abstract void updateObjectGW(GameWorld gw);
}
