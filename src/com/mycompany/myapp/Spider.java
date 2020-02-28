// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import java.util.Random;
import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

/** Class Spider inherits from Class MovableObject, and serves to 
 *  be the class for enemies in game. Spider class holds attributes/ methods
 *  that describe what the spider does in game.*/

public class Spider extends MovableObject
{
	private int angleDifference = 25; // When needing change in heading, use this.
	private int damageAmount = 1;     // Amount of damage caused to Ant class when contacted.
	private int boundryLimitX; // Max X Axis Limit for boundry
	private int boundryLimitY; // Max Y Axis Limit for boundry
	private GameWorld localGW;            // Holds instance of GameWorld.
	
	private Vector<GameObject> collisionVectorMemory  = new Vector<GameObject>(); // Collision Record Memory

	// Constructor that records input of location, color, size, heading, and speed.
	public Spider(Point inputLocation, int inputColor, int inputSize, int iHeading, int iSpeed) 
	{
		super(inputLocation, inputColor, inputSize, iHeading, iSpeed);		
	}

	// The Default color of the spider shouldn't be changed. 
	public void setColor()
	{
		// Deny the option to change color.
	}

	// Return the amount of damage spider causes.
	public int obtainDamageAmount()
	{
		return damageAmount;
	}
	
	// Set out of bound Limits for Spider.
	// NEEDS TO CHANGE.
	public void mapLimits(int x, int y)
	{
		boundryLimitX = x;
		boundryLimitY = y;
	}
	
	// toString() method serves to display detailed information about current 
	// spider class in one line.
	public String toString()
	{
		Point currentLoc = obtainLocation();   // Get current location.
		int givenColor = obtainColor();        // Get spider color

		String myLocationDesc = "loc=" + currentLoc.getX() + "," 
						               + currentLoc.getY() + " ";
		String myColorDesc = "color=" + "[" + ColorUtil.red(givenColor) + ","
						                + ColorUtil.green(givenColor) + ","
						                + ColorUtil.blue(givenColor) + "] ";
		String headDesc = "heading=" + obtainHeading() + " ";
		String speedDesc = "speed=" + obtainSpeed() + " ";		
		String mySizeDesc = "size=" + obtainSize() + " ";
		
		return "Spider: " + myLocationDesc + myColorDesc + headDesc 
				       + speedDesc + mySizeDesc; 
	}
	
	// Determine if spider is out of bounds and redirect spider heading
	public void watchEnemyBounds(int mapOriginX, int mapOriginY)
	{
		int size = this.obtainSize();
		float centerTopY = (this.obtainLocation().getY() + mapOriginY + (size)); // For bottom
		
		float centerBotRightX = this.obtainLocation().getX() + mapOriginX + (size); // For Right Side
		float centerRightX = this.obtainLocation().getX() + mapOriginX;
		
		float topBotY = this.obtainLocation().getY() + mapOriginY;  // For Top.
		
		int currentHeading = obtainHeading();
		float eboundX = (float) (boundryLimitX + mapOriginX);
		float eboundY = (float) (boundryLimitY + mapOriginY);
		
		Random ona = new Random();    // Random number generator
		int changeCourse = ona.nextInt(180) + 90; // Has to be aleast 90-180ish
		
		if ( (centerTopY > eboundY) || (centerBotRightX > eboundX) ||
			 (topBotY < mapOriginY) || (centerRightX < mapOriginX))
		{
			setHeading(currentHeading + changeCourse);
			System.out.println("boundryX: " + boundryLimitX + " | " + "boundryY: " + boundryLimitY);
			System.out.println("centerTopY: " + centerTopY + " | " + "centerBotRightX: " + centerBotRightX 
					            + "\n" + "centerRightX: " + centerRightX + " | " + "topBotY: " + topBotY);
			System.out.println("currentHeading: " + currentHeading);
		}
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		float mapViewX = pCmpRelPrnt.getX();
		float mapViewY = pCmpRelPrnt.getY();
						
		float centerPointX = this.obtainLocation().getX() + mapViewX;
		float centerPointY = this.obtainLocation().getY() + mapViewY;
		
		int width = this.obtainSize(), height = this.obtainSize();
		int topPointX    = (int) centerPointX;
		int topPointY    = (int) centerPointY + (height / 2);
		
		int leftBottomX  = (int) centerPointX + (width / 2);
		int leftBottomY  = (int) centerPointY - (height / 2);
		
		int rightBottomX = (int) centerPointX - (width / 2);
	    int rightBottomY = (int) centerPointY - (height / 2);
	    
	    int [] listX = new int[] {topPointX, leftBottomX, rightBottomX};
	    int [] listY = new int[] {topPointY, leftBottomY, rightBottomY};
		
		// Draw Flag Model: Triangle - GREEN.
		g.setColor(ColorUtil.BLACK);
		g.drawPolygon(listX, listY, 3);
		g.setColor(ColorUtil.YELLOW);
		g.drawLine((int)centerPointX, (int)centerPointY, (int)topPointX, (int) topPointY);
	    g.drawLine((int)centerPointX, (int)centerPointY, (int)leftBottomX, (int) leftBottomY);
		g.drawLine((int)centerPointX, (int)centerPointY, (int)rightBottomX, (int) rightBottomY);
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
		int distanceBetweenCenters = ( (deltaX * deltaX) + (deltaY * deltaY) );
		
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

	// Only important collision is with ANT.
	@Override
	public void handleCollision(GameObject otherObject) 
	{
		System.out.println("Handle Collision of SPIDER with anything: ");
		if (otherObject instanceof Ant)   // Check for collision with ANT.
		{
			//System.out.println("Handle Collision with ANT");
			//localGW.enemyInteraction('A', this);             // If Ant found; then pass self to collision handeling method.
		}
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

	@Override
	public void updateObjectGW(GameWorld gw) 
	{
		localGW = gw;
	}
	
	
}
