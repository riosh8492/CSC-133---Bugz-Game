// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/** Class FoodStation inherits from Class Fixed Object. 
 *  FoodStation serves to be the class that holds the 
 *  food supply that the Ant will need throughout the game.
 *  The gameObject is Fixed in its Location and has food capacity.*/

public class FoodStation extends FixedObject implements ISelectable
{
	private int capacity;             // Holds current level of Food.
	private Point mapViewPoint;
	private GameWorld localGW;
	boolean isSelected = false;
	
	private Vector<GameObject> collisionVectorMemory = new Vector(); // Collision Record Memory
	
	//This sets it up, and sets food limit to what the size may be. 
	public FoodStation(Point inputLocation, int inputColor, int inputSize, int foodStock) 
	{
		super(inputLocation, inputColor, inputSize+30);
		capacity = foodStock;         // Set the limit of food to the possible random size.
	}
	
	// Returns the amount of Food in FoodStation.
	public int obtainCapacity()
	{
		return capacity;
	}

	// Reduces the capacity of foodStation by a bit.
	public void consumeCapacity()
	{
		capacity -= 10; //When activated/touched -> Lower food amount in station.
	}
	
	// Reduces capacity to ZERO.
	public void emptyCapacity()
	{
		capacity = 0;
	}
	
	// Update Flag color to be FADED. Regular Green -> Faded Green
	public void fadeFoodStationColor()
	{
		int fadedColor = ColorUtil.rgb(200, 200, 225);     // Holds faded green color
		super.setColor(fadedColor);                              // Sets color to faded green.
	}
	
	// Overwriting toString to output formatted info.
	public String toString()
	{
		int givenColor = obtainColor();                      // Get current Color
		Point currentLoc = obtainLocation();                 // Get current location
		String locDesc = "loc=" + currentLoc.getX() + " ,"   // Get each location X/Y
				                + currentLoc.getY() + " ";
		String colorDesc = "color=" + "[" + ColorUtil.red(givenColor) + ","       // Get each color rgb values.
                                          + ColorUtil.green(givenColor) + ","
                                          + ColorUtil.blue(givenColor) + "] ";
		String capDesc = "capacity=" + capacity + " ";                            // Get current capacity amount.
		 
		return "FoodStation: " + locDesc + colorDesc + capDesc;                   // return line of info describing FoodStation.
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		float mapViewX = pCmpRelPrnt.getX(); // MapView X coordinate.
		float mapViewY = pCmpRelPrnt.getY(); // MapView Y coordinate.
		
		float localPointX = this.obtainLocation().getX() + mapViewX;  // Get mapView location
		float localPointY = this.obtainLocation().getY() + mapViewY;  // Get mapView Location
		
		int width = this.obtainSize(), height = this.obtainSize();  // +40 to scale FoodStations up to size.
		
		//this.setLocation(new Point(localPointX, localPointY)); // Save MapViewAdjusted View
		if (isSelected == false)
		{
			g.setColor(ColorUtil.BLUE);
			g.fillRect((int) localPointX, (int) localPointY, width, height);
			g.setColor(ColorUtil.WHITE);
			g.drawString(String.valueOf(capacity), (int) localPointX + (width/2), (int) localPointY + (height/2));
		}
		
		if (isSelected == true)
		{
			// Change Color to When Selected. 
			g.setColor(ColorUtil.BLACK);
			g.drawRect((int) localPointX, (int) localPointY, width, height);
		}
	}

	@Override
	public boolean collidesWith(GameObject otherObject) 
	{
		boolean hasCollided = false;
		int width  = this.obtainSize();
		int height = this.obtainSize();
		Point mvOrigin = localGW.obtainMapViewOrigin();

		float thisCenterX = this.obtainLocation().getX() + mvOrigin.getX() + (width/2);  // Get current object 'Center'.
		float thisCenterY = this.obtainLocation().getY() + mvOrigin.getY() + (height/2);
		
		float otherCenterX = otherObject.obtainLocation().getX() + mvOrigin.getX() + (otherObject.obtainSize()/2); // Get other object Center.
		float otherCenterY = otherObject.obtainLocation().getY() + mvOrigin.getY() + (otherObject.obtainSize()/2);
		
		// find distance between centers (use square, to avoid taking roots)
		int deltaX = (int) (thisCenterX - otherCenterX);
		int deltaY = (int) (thisCenterY - otherCenterY);
		int distanceBetweenCenters = ( (deltaX * deltaX) + (deltaY * deltaY));
		
		// find square of sum of radii
		int thisRadius = this.obtainSize() / 2;
		int otherRadius = otherObject.obtainSize() / 2; 
		
		int radiusSquare = ( thisRadius * thisRadius + 
				             2 * thisRadius * otherRadius + 
				             otherRadius * otherRadius );
		
		if (distanceBetweenCenters <= radiusSquare)
		{
			if (!containsCollisionObject(otherObject))
			{
				hasCollided = true; // Set Collison Boolean to True.
			}
		}
		return hasCollided;
	}

	// ONLY important collision is with ANT.
	@Override
	public void handleCollision(GameObject otherObject) 
	{
		if (otherObject instanceof Ant)
		{
			localGW.consumeFoodStation(this); // Pass this object as the contacted FoodStation
		}
		localGW.callObservers();
	}
	
	// Add Object to list of Collided Objects.
	public void addCollisionObject(GameObject id)
	{
		collisionVectorMemory.addElement(id);
	}
	
	// If the Object exists in Collection, remove it.
	public void removeCollisionObject(GameObject id)
	{
		boolean removed;
		
		if (id != null) 
		{
			if (collisionVectorMemory.contains(id))
			{
				removed = collisionVectorMemory.remove(id);
			}
		}
	}
	
	//Returns true if input in Vector Collision Collection
	public boolean containsCollisionObject(GameObject id)
	{
		if (collisionVectorMemory.contains(id))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void setLocation(Point input)
	{
		if (isSelected == true)
		{
			super.setLocation(input);
		}
	}

	@Override
	public void updateObjectGW(GameWorld gw) 
	{
		localGW = gw;		
	}

	@Override
	public void setSelected(boolean yesNo) 
	{
		isSelected = yesNo;
	}

	@Override
	public boolean isSelected() 
	{
		return isSelected;
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) 
	{
		float iShapeX = this.obtainLocation().getX(); // Upper Left Corner
		float iShapeY = this.obtainLocation().getY(); // 
		
		int parentX = (int) pPtrRelPrnt.getX(); // Pointer location relative to Parent's Origin.
		int parentY = (int) pPtrRelPrnt.getY();
		
		int localX = (int) (pCmpRelPrnt.getX() + iShapeX); // MapView maybe?
		int localY = (int) (pCmpRelPrnt.getY() + iShapeY);
		
		int width = this.obtainSize();
		int height = this.obtainSize();
		
		//System.out.println();
		
		
		if ( (parentX >= localX) && (parentX <= localX+width) && 
			 (parentY >= localY) && (parentY <= localY+height) )
		{
			return true;
		}
		else 
		{	
			return false;
		}
	}
	
}
