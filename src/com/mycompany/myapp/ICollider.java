package com.mycompany.myapp;

/** Intended for performing collision 
 *  detection, and Response.*/

public interface ICollider 
{
	// apply appropriate detection algorithm
	boolean collidesWith(GameObject otherObject);
	
	// apply appropriate response algorithm
	void handleCollision(GameObject otherObject);
}
