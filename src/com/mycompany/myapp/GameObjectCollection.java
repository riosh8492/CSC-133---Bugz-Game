// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import java.util.ArrayList;

/** Class GameOjectCollection implements ICollection.
 *  Purpose is to hold the collection of GameObjects 
 *  in GameWorld. Provides an Iterator as well. 
 *  OVERALL, it implements the ITERATOR DESIGN PATTERN.*/

public class GameObjectCollection implements ICollection
{
	private ArrayList<GameObject> iCollection;    // Collection variable storage.
	
	// Create empty ArrayList to hold GameObjects
	public GameObjectCollection()
	{
		iCollection = new ArrayList<GameObject>();
	}

	// Adds to the the current GameObject Storage.
	@Override
	public void add(GameObject newBoi) 
	{
		iCollection.add(newBoi);   // Add new GameObject to iCollection
	}

	// Return an Iterator for the collection of Objects.
	@Override
	public IIterator getIterator() 
	{
		return new GearIterator();
	}
	
	
	/** Private class GearIterator Implements IIterator. To be 
	 *  used by GameObjectCollction class for object instance of
	 *  an iterator. */
	private class GearIterator implements IIterator 
	{
		private int indexDesc; // Index holder for record storage.
		
		// Constructor; Set index at -1. 
		public GearIterator()
		{
			indexDesc = -1;
		}

		// Checks if there are more elements to be processed
		@Override
		public boolean hasNext() 
		{			
			if (iCollection.size() == 0)  // If no more elements, return false
			{
				return false;
			}
			if (indexDesc == (iCollection.size() - 1))   //If at the limit, return false.
			{
				return false;
			}
			
			return true;     // Return true if there are still elements in collection
		}

		// Return next Object in iCollection. 
		@Override
		public GameObject getNext() {
			// TODO Auto-generated method stub
			indexDesc += 1;
			
			// Catches if index goes out of bounds.
			if (indexDesc == iCollection.size())
			{
				return null;
			}
			
			return iCollection.get(indexDesc);
		}

	}
}












