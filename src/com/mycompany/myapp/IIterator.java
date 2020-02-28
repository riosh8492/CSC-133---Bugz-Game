// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

/** IIterator Interface for Iterator Design Pattern.
 *  Used to implement Iterator in ICollection.*/

public interface IIterator 
{
	public boolean hasNext();    // Return T/F if there exists an object next in collection
	public GameObject getNext(); // Return the next object in collection.
}
