// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import java.util.Random;
import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

/** Class Ant inherits from Class MovableObject, and implements 
 *  Interface ISteerable, AND uses the SINGETON Design Pattern.
 *  Class Ant is meant to hold attributes, and methods relating 
 *  to the Ant gameObject within the game.
 *  */

public class Ant extends MovableObject implements ISteerable 
{
	private static Ant singleAnt;         // Holds a single instance of Ant. 
	private int maximumSpeed = 40;        // Holds max speed Ant can possibly go.
	private int foodLevel = 30;           // Holds the current FoodLevel of Ant.
	private int foodConsumptionRate = 5;  // Holds the rate in which Ant consumes food.
	
	private int healthLevel = 10;         // Holds health bar, which starts are 10.
	private int lastFlagReached = 1;      // Holds the last flag touched by Ant, Starting: Flag: 1.
	private Point firstFlagPosition;
	private GameWorld localGW;            // Holds instance of GameWorld.
	
	private Vector<GameObject> collisionVectorMemory = new Vector<GameObject>(); // Collision Record Memory
	
	//Constructor that records inputs to Ant attributes.
	private Ant(Point inputLocation, int inputColor, int inputSize, int iHeading, int iSpeed)
	{
		super(inputLocation, inputColor, inputSize, iHeading, 0);
		firstFlagPosition = inputLocation; // Save First Flag Position.
	}
	
	// Use this to obtain new/current Single Ant. 
	public static Ant getAnt(Point inputLocation, int inputColor, int inputSize, int iHeading, int iSpeed)
	{
		if (singleAnt == null)  // If No Ant exists, create a new one.
		{
			singleAnt = new Ant(inputLocation, inputColor, inputSize, iHeading, iSpeed);
		}
		return singleAnt;       // Return current or new Ant instance. 
	}
	
	// Return the single instance of Ant. 
	public Ant getAnt()
	{
		return singleAnt;
	}

	// Overwrites toString function to display custom information regarding Ant Class.
	// toString() builds the output of info into one line for user to read. 
	public String toString()
	{
		Point currentLoc = obtainLocation();         // Get current location of Ant.
		int givenColor = obtainColor();              // Get current color of Ant.

		String myLocationDesc = "loc=" + currentLoc.getX() + "," 
						               + currentLoc.getY() + " ";
		String myColorDesc = "color=" + "[" + ColorUtil.red(givenColor) + ","
						                + ColorUtil.green(givenColor) + ","
						                + ColorUtil.blue(givenColor) + "] ";
		String headDesc = "heading=" + obtainHeading() + " ";
		String speedDesc = "speed=" + obtainSpeed() + " ";		
		String mySizeDesc = "size=" + obtainSize() + " ";
		String maxSpeed = "maxSpeed=" + maximumSpeed + " ";
		String consumeRate = "foodConsumptionRate=" + foodConsumptionRate + " ";
		
		return "Ant: " + myLocationDesc + myColorDesc + headDesc 
				       + speedDesc + mySizeDesc + maxSpeed
				       + consumeRate;
	}
	
	/* Give info on Ant's status. */
	// Return current health of Ant.
	public int obtainHealth()
	{
		return healthLevel;
	}
	
	// Return current food level of Ant
	public int obtainFoodLevel()
	{
		return foodLevel;
	}
	
	// Return current max speed of Ant.
	public int obtainSpeedCap()
	{
		return maximumSpeed;
	}
	
	// Return current Flag reached by Ant
	public int obtainRecentFlag()
	{
		return lastFlagReached;
	}
	
	//Updates Ant's most recent flag reached.
	public void updateRecentFlag(int recentFlag)
	{
		lastFlagReached = recentFlag;
	}
	
	// Updates Ant's current foodLevel.
	public void updateFoodLevel(int newFoodLevel)
	{
		foodLevel = newFoodLevel;
	}
	
	public void resetLocPosition()
	{
		this.setLocation(firstFlagPosition);
	}
	
	// Updates Ant's Health.
	public void reduceAntHealth(int healthLoss)
	{
		healthLevel -= healthLoss;
	}

	// Slowly fades Ant Color.
	public void fadeAntColor()
	{
		int fadeRate = 10;                 // Holds rate in which Ant's color fades.
		int currentColor = obtainColor();  // Get current Ant color.
		
		//Increments the fading color of the Ant
		//Saves it for when its to be faded again.
		setColor(ColorUtil.rgb( ColorUtil.red(currentColor), 
								ColorUtil.green(currentColor + fadeRate), 
								ColorUtil.blue(currentColor + fadeRate)) );
	}
	
	//Reduce Ant Speed by a certain amount.
	public void reduceAntSpeed()
	{
		int antSpeed = obtainSpeed();     // Get current speed.
		int slowRate = 10, returnSpeed;   // Hold slow rate, and 'total' variable.
		
		//Reduce speed, but have lowest speed be one.
		returnSpeed = ((antSpeed - slowRate) >= 0) ? (antSpeed - slowRate) : 1;
		
		//Update Ant's Speed.
		adjustSpeed(returnSpeed);
	}
	
	// Consume current foodLevel based on foodConsumptionRate variable.
	public void burnCalories()
	{
		// DISABLED FOR TESTING. WHEN FINSIHED -> ENABLE!
		//foodLevel -= foodConsumptionRate;
	}
	
	// Used to reset food level when Ant is reset after losing life. 
	public void resetHealthFoodLevel()
	{
		foodLevel = 30;
		healthLevel = 10;
	}
	
	public void updateObjectGW(GameWorld gw)
	{
		localGW = gw;
	}

	
	/* Below are concrete methods based on abstract methods from Interface ISteerable*/
	@Override
	public void adjustHeading(int newDirect) {
		super.setHeading(newDirect);
	}

	@Override
	public void adjustSpeed(int newVelocity) {
		super.setSpeed(newVelocity);
	}

	// Draw Ant Image Here.
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		float mapViewX = pCmpRelPrnt.getX();
		float mapViewY = pCmpRelPrnt.getY();
		int startAngle = 0, arcAngle = 360;
		
		float adjustedSize  = (float) (this.obtainSize());// + 30.0);
		
		float antPosX = (this.obtainLocation().getX() - (adjustedSize/2));
		float antPosY = (this.obtainLocation().getY() - (adjustedSize/2));
	
		//System.out.println("ANTCenter Point: " + (this.obtainLocation().getX()+mapViewX));
		//System.out.println("ANTCenter Point: " + (this.obtainLocation().getY()+mapViewY));
		
		int centerX = (int) (mapViewX + antPosX),
		    centerY = (int) (mapViewY + antPosY);
		
		
		// Draw Ant Model: Circle.
		//myXform.transformPoint(center, center); // transform points
		//centerX = (int) center[0];
		//centerY = (int) center[1];

		g.setColor(ColorUtil.CYAN);
		g.fillArc(centerX, centerY, (int) adjustedSize, (int) adjustedSize, startAngle, arcAngle);
		g.setColor(ColorUtil.BLACK);
		// This is the Center. Its a fucking box. 
		g.drawLine(0, 0, centerX+this.obtainSize()/2, centerY+this.obtainSize()/2);
		//System.out.println("centerXY: X: " + centerX + " | Y:" + centerY);
		
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
				System.out.println("Collision Detection - ANT:");
				System.out.println("ThisCenterX: " + thisCenterX + ", ThisCenterY" + thisCenterY);
				System.out.println("otherCenterX: " + otherCenterX + ", otherCenterY" + otherCenterY);
				System.out.println("=============================");
				hasCollided = true; // Set Collison Boolean to True.
			}
		}
		return hasCollided;
	}

	@Override
	public void handleCollision(GameObject otherObject) 
	{
		Flag checkpoint;
		Spider bogie;
		FoodStation resource;
		
		int updateFlag;
		int stationCap;
		
		System.out.println("Handle Collision of Ant with anything: ");
		if (otherObject instanceof Spider)  // SPIDER COLLISION
		{
			System.out.println("Handle Collision of Ant with SPIDER");
			localGW.enemyInteraction('A', otherObject);  // Call collision method.
		}
		else if (otherObject instanceof Flag) // FLAG COLLISION
		{
			System.out.println("Handle Collision of Ant with FLAG");
			
			checkpoint = (Flag) otherObject;
			localGW.flagContact(checkpoint.obtainFlagNum());
		}
		else if (otherObject instanceof FoodStation)
		{
			System.out.println("Handle Collision of Ant with FOODSTATION");
			resource = (FoodStation) otherObject;
			localGW.consumeFoodStation(resource);
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

	// Call a change in heading if Ant is out of bounds.
	public void watchBounds(int absoluteX, int absoluteY, int width, int height) 
	{

		// Ant information Bounds.
		
		
		int currentHeading = obtainHeading();
		
		Random ona = new Random();    // Random number generator
		int changeCourse = ona.nextInt(180) + 90;
		System.out.println("Handle Ant out of bounds here ..");
		//System.out.println("boundryX: " + boundryLimitX + " | " + "boundryY: " + boundryLimitY);
		//System.out.println("cornerL: " + cornerL + " | " + "cornerR: " + cornerR 
		//		            + "\n" + "top: " + top + " | " + "bottom: " + bottom);
		
		/*if ( (centerTopY > boundryLimitY) || (centerBotRightX > boundryLimitX) ||
			 (topBotY < mapOriginY) || (centerRightX < mapOriginX))
		{
			setHeading(currentHeading - changeCourse);
		}*/
	}
}
