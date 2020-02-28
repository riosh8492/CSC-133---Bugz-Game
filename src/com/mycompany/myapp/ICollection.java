// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

/** ICollection is an interface implementation that 
 *  is based on the Iterator Design Pattern.*/

public interface ICollection 
{
	public void add(GameObject newItem);         // Add to Collection
	public IIterator getIterator();              // Obtain an Iterator.
}
