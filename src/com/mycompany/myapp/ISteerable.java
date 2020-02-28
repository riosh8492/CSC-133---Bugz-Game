// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

/** Interface ISteerable serves to provide abstract methods
 *  to Movable classes that are also 'Steerable'. */

public interface ISteerable {
	public void adjustHeading(int newHeading);   // Abstract method to change Heading.
	public void adjustSpeed(int newSpeed);       // Abstract method to change speed.
}
