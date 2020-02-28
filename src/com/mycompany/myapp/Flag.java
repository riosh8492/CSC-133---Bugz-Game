// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/** Class Flag inherits from Class FixedObject.
 *  Flag class serves to hold attributes/methods
 *  to describe Flag gameObject in the Game.*/

public class Flag extends FixedObject implements ISelectable
{
	private int sequenceNumber;              // Flag Number that it has.
	private Vector<GameObject> collisionVectorMemory = new Vector(); // Collision Record Memory
	private GameWorld localGW;
	private boolean isSelected = false;

	// Constructor that records input color, size, location, and flag number.
	public Flag(Point inputLocation, int inputColor, int inputSize, int inputSeq) {
		super(inputLocation, inputColor, inputSize);

		sequenceNumber = inputSeq;           // Record current flag number.
	}
	
	public int obtainFlagNum()
	{
		return sequenceNumber;
	}
	
	// Blocking the changing of color of Flag.
	public void setColor(int changeColor)
	{
		// Deny the ability to change the color of current flag.
	}
	
	//Overwriting toString to output formatted info.
	public String toString()
	{
		int givenColor = obtainColor();         // Get current Color
		Point currentLoc = obtainLocation();    // Get current location.

		String myColorDesc = "color=" + "[" + ColorUtil.red(givenColor) + ","    // Get rgb values
						                + ColorUtil.green(givenColor) + ","
						                + ColorUtil.blue(givenColor) + "] ";
		String myLocationDesc = "loc=" + currentLoc.getX() + ","                 // Get specific location X/Y
						               + currentLoc.getY() + " ";
		String mySizeDesc = "size=" + obtainSize() + " ";                        // Get size of gameObject.
		String mySeqDesc = "seqNum=" + sequenceNumber;                           // Get current sequence number ( Flag number )
		
		return "Flag: " + myLocationDesc + myColorDesc + mySizeDesc + mySeqDesc; // Return line of info for Flag.
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		float mapViewX = pCmpRelPrnt.getX();
		float mapViewY = pCmpRelPrnt.getY();
		
		//System.out.println("MapView: X:" + mapViewX + " / Y:" + mapViewY);
		float adjustedSize = (float) this.obtainSize();
		
		float centerPointX = (adjustedSize/2) + this.obtainLocation().getX() + mapViewX;
		float centerPointY = (adjustedSize/2) + this.obtainLocation().getY() + mapViewY;
		
		int botPointX    = (int) centerPointX;
		int botPointY    = (int) (centerPointY + (adjustedSize / 2));
		//System.out.println("TopPoint: X:" + topPointX + " / Y:" + topPointY);
		
		int leftTopX  = (int) (centerPointX - (adjustedSize / 2));
		int leftTopY  = (int) (centerPointY - (adjustedSize / 2));
		//System.out.println("leftBottomX: X:" + leftBottomX + " / Y:" + leftBottomY);
		
		int rightTopX = (int) (centerPointX + (adjustedSize / 2));
	    int rightTopY = (int) (centerPointY - (adjustedSize / 2));
	    //System.out.println("rightBottomX: X:" + rightBottomX + " / Y:" + rightBottomY);
	    
	    int [] listX = new int[] {botPointX, leftTopX, rightTopX};
	    int [] listY = new int[] {botPointY, leftTopY, rightTopY};
		
		// Draw Flag Model: Triangle - GREEN.
		g.setColor(ColorUtil.YELLOW);
		//g.fill
		//g.drawPolygon(listX, listY, 3);
		g.fillPolygon(listX, listY, 3);
		//(leftBottomX, leftBottomY, topPointX, topPointY, rightBottomX , rightBottomY);
		g.setColor(ColorUtil.BLACK);
		g.drawString(String.valueOf(sequenceNumber), (int) centerPointX-15, (int) centerPointY-15);

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
	
	// Only if paused, and selected can we change location. 
	public void setLocation(Point input)
	{
		if (isSelected == true) // needs to change.
		{
			super.setLocation(input);
		}
	}

	// Only important Collision is with ANT.
	@Override
	public void handleCollision(GameObject otherObject) 
	{
		if (otherObject instanceof Ant)
		{
			System.out.println("Flag Contaced Ant -> Called from Handeling method.");
			localGW.flagContact(sequenceNumber);
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
