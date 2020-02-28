// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.Border;

/** Class MapView extends Observer.
 *  Purpose is mainly to react to 
 *  any changes in its Observable (GameWorld).
 *  Reacts any significant changes occur..*/

public class MapView extends Container implements Observer, ActionListener 
{
	private GameWorld playField;         // Hold Instance of GameWorld
	private IIterator vaultDial;  // Hold Objects of GameWorld
	private boolean moveLocation = false;
	private GameObject selectedItem = null; // To be managed.
	
	// Record GameWorld, register Observer, and set RED border.
	public MapView(Observable tierra)
	{
		this.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.rgb(200, 0, 0)));
		tierra.addObserver(this);       // Add itself as an Observer to GameWorld.
		
		// Display to console all information of GameWorld to console.
		playField = (GameWorld) tierra;
		playField.displayGameState();
	}
	
	// Gathers Pointer Location.
	public void pointerPressed(int x, int y)
	{
		GameObject bookItem;
		ISelectable selectCandidate;
		int localX = x - getParent().getAbsoluteX();
		int localY = y - getParent().getAbsoluteY();
		boolean seenObject = false; // False -> Nothing was found. True -> New Selected. 
		Point pPtrRelPoint = new Point(localX, localY);
		Point pCmpRelPoint = new Point(this.getAbsoluteX(), this.getAbsoluteY());
		
		System.out.println("pPtrRelPoint - X: " + x + ", Y: " + y);
		System.out.println("pCmpRelPoint - X: " + localX + ", Y: " + localY);
		
		IIterator storage = playField.returnIterator();
		if (playField.obtainPause() == true) // Game is Paused.
		{
			System.out.println("I am PAUSED");
			//moveLocation = false;
			// Before Moving on to check
			
			while (storage.hasNext()) // Produces a Seleectable
			{
				bookItem = storage.getNext();
				if (bookItem instanceof ISelectable)  // Check if can be selected.
				{
					selectCandidate = (ISelectable) bookItem;  // Set variable as such 
					if (selectCandidate.contains(pPtrRelPoint, pCmpRelPoint))
					{
						selectCandidate.setSelected(true);
						selectedItem = (GameObject) selectCandidate; // Record Selected GameObject
						//moveLocation = true;
						seenObject = true; // Since local variable resets every call; not need to set to false at end.
						System.out.println("I WAS SELECTED as a Selectable.");
					}
					else
					{
						selectCandidate.setSelected(false);
						System.out.println("Object made UnSelectable");
					}
				}
			}
			
			
			// Change the Location. 
			if ((moveLocation == true) && (selectedItem != null) && (seenObject == false))
			{
				// Change Selectable Object Location.
				System.out.println("ChangingLoc");
				selectedItem.setLocation(new Point(localX, localY));
				
				moveLocation = false;
			}			
		}
		repaint(); // Idk just calling it again. 
	}

	@Override
	public void update(Observable seenObject, Object arg) {
		// TODO Auto-generated method stub
		// Code here to to output a view based on the data in the Observable.
		// Code here to call the method in GameWorld (Observable) that output
		// the game object information to the console. 
		
		playField = (GameWorld) seenObject; // Save instance of GameWorld before repaint().
		playField.displayMap(); 
		this.repaint();
	}
	
	// Override Paint method.
	// Iterate through all gameObjects and call draw() on each Object. 
	@Override
	public void paint(Graphics G)
	{
		Point       mapViewLocDesc = new Point(this.getAbsoluteX(), this.getAbsoluteY());
		GameObject  erogeItem;
		Ant         singleAnt;
		Flag        checkPoint;
		FoodStation breadBox;
		Spider      vietPong;
		
		super.paint(G);
		
	    Transform myXform = Transform.makeIdentity();
	    G.getTransform(myXform);
	    
	    //myXform.rotate((float) Math.toRadians(14), mapViewLocDesc.getX(), mapViewLocDesc.getY());
	    
	    //myXform.translate(getAbsoluteX(),getAbsoluteY());
		//apply translate associated with display mapping
	    //myXform.translate(0, getHeight());
		//apply scale associated with display mapping
	    //myXform.scale(1, -1);
		//move drawing coordinates so that the local origin coincides with the screen origin
	    //myXform.translate(-getAbsoluteX(),-getAbsoluteY());
		//G.setTransform(myXform);
	    
		// Insert loop that goes through each GameObject and calls draw() method. 
		vaultDial = playField.returnIterator();
		while(vaultDial.hasNext())
		{
			erogeItem = vaultDial.getNext();
			
			if (erogeItem instanceof Ant)
			{
				singleAnt = (Ant) erogeItem;
				singleAnt.draw(G, mapViewLocDesc);
				//System.out.println("Ant being drawn.");
			}
			else if (erogeItem instanceof Flag)
			{
				checkPoint = (Flag) erogeItem;
				checkPoint.draw(G, mapViewLocDesc);
				//System.out.println("Flag being drawn.");
			}
			else if (erogeItem instanceof FoodStation)
			{
				breadBox = (FoodStation) erogeItem;
				breadBox.draw(G, mapViewLocDesc);
				//System.out.println("Foodstation being drawn.");
			}
			else if (erogeItem instanceof Spider)
			{
				vietPong = (Spider) erogeItem;
				vietPong.draw(G, mapViewLocDesc);
				//System.out.println("Spider being drawn.");
			}
			else
			{
				System.out.println("Lmao bruh.");
			}
		}
		G.resetAffine(); // Reset transformations
	}

	// For when Position Command is pressed.
	@Override
	public void actionPerformed(ActionEvent evt) 
	{
		if (playField.obtainPause() == true) // If the Game is paused -> use Position command.
		{
			System.out.println("Pressing Position Command.");
			moveLocation = true;
		}
		else 
		{
			moveLocation = false;
		}
		
		
	}
}
