// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.lang.Character;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

/** Class GameWorld is meant to be the landscape of the Game. It holds all gameObjects 
 *  that are meant to be created at the start. It also manages the internal clock 
 *  that times the world. More importantly, the class holds various attributes and 
 *  methods that manipulate the gameObjects for certain actions in the Game. 
 *  Such examples include object collision, random number generators for 
 *  location/speed/heading/etc, handling affects of gameObjects interactions. 
 * */

public class GameWorld extends Observable
{
	private int clockTimer       = 0;     // Records how many ticks have passed.
	private int remainingLives   = 3;     // Records how many lives Ant has.
	private int flagLimit        = 4;     // Limits how many flags allowed.
	private int foodStationLimit = 2;     // Limits how many foodStations there are.
	private int spiderLimit      = 2;     // Limits how many spiders there are.
	
	private Sound antSpiderSound;
	private Sound antFoodStationSound;
	private Sound antFlagSound;
	private BGSound backgroundMusic;
	
	private int originX;    // Record the Lower limit of points.
	private int originY;    // Record the lower limit of points. 
	private int axisXLimit = 300;         // Sets limits of random location generation.
	private int axisYLimit = 300;          // Sets limits of random location generation.
	
	private int updateFlag = 1;   // Holder for Current Flag reached by Ant.
	private int enemyHeadingStart = 359;    // inital random heading number for generation
	private int currentHealth = 10;        
	private int currentFoodLevel = 30;
	private Point intialFlag = null;
	private boolean soundStatus  = true;    // handles when sound is ON or OFF
	private boolean gameOver     = false;    // Handles when the game is to be Exited.
	private boolean gamePaused   = false; // False -> PLayMode, True -> Pause Mode

	// ArrayList that holds type 'GameObject'. Meant to hold all GameObjects in the Game.
	private GameObjectCollection mainObjectCollection = new GameObjectCollection();
	
	private static int antColor         = ColorUtil.rgb(255, 40, 40);   // Standard Ant Color: RED
	private static int spiderColor      = ColorUtil.rgb(40, 110, 240);  // Standard Spider Color: BLUE
	private static int flagColor        = ColorUtil.rgb(180, 40, 180);  // Standard Flag Color: PURPLE
	private static int foodStationColor = ColorUtil.rgb(20, 220, 90);   // Standard FoodStation Color: GREEN

	private Ant                playerOne;  // Holds Main Ant Player
	private Spider             bogie;     // Holds Spiders that are randomly generated.
	private FoodStation        oasis;      // Holds FoodStations that are randomly generated.
	private Flag               milestone;  // Holds Flags that are randomly generated.

	/** Init() method helps initialize the first gameObjects in the Game
	 *  Examples include: 2 Spiders, 4 Flags, and 2 FoodStations. 
	 *  gameObjects are also placed in ICollection for Storage.
	 * */
	public void init()
	{
		// Code here to create the initial game objects/setup
		int count, innerCount;          // Loop count holders.
		Point startingLine = null;      // Record where first Flag placed for Ant.
		
		backgroundMusic = new BGSound("bgMusic.wav");
		backgroundMusic.run();  // Run looping background Music
		
		// Generates 'flagLimit' amount of Flags randomly, and stores them in collection.
		for (count = 1; count <= flagLimit; count++)
		{
			milestone = new Flag(genPoint(), flagColor, 45, count); // Create Flag instance with random location.

			mainObjectCollection.add(milestone);    // Store Flag in collection.

			// User ternary operation to record Point location of first Flag.
			startingLine = (count == 1) ? milestone.obtainLocation() : startingLine;
			System.out.println("FLAG Position: " + "X: " + milestone.obtainLocation().getX() + 
					           " | " + "Y: " + milestone.obtainLocation().getY());
			intialFlag = startingLine;    // Record Flag 1 Point for Ant repositioning.
		}		
		
		startingLine = adjustAntPos(startingLine);
		
		// Creation of Single Ant at a fixed location of first Flag.
		playerOne = Ant.getAnt(startingLine, antColor, 50, 0, 25);          // Create Ant instance with color/speed/heading.
		System.out.println("Ant Position: " + "X: " + startingLine.getX() + " | " + "Y: " + startingLine.getY());
		mainObjectCollection.add(playerOne);
		
		//System.out.println("AXIS LIMIT: " + axisXLimit + " | " + axisYLimit);
		
		// Generates 'spiderLimit' amount of Spiders randomly on the gameWorld
		for (count = 0; count < spiderLimit; count++)
		{
			bogie = new Spider(genPoint(), spiderColor, 50, genHeading(enemyHeadingStart), 20);  // Create Spider instance with random aspects. 
			System.out.println("SpiderLoc: " + "X: " + bogie.obtainLocation().getX() + "| Y: " + bogie.obtainLocation().getY());
			bogie.mapLimits(axisXLimit, axisYLimit);  // Set bounds for Spider.
			mainObjectCollection.add(bogie);
		}
		
		//Generates 'foodStationLimit' amount of FoodStations randomly in gameWorld.
		for (count = 0; count < foodStationLimit; count++)
		{
			innerCount = genSize();                                                          // Holding random size generation.
			oasis = new FoodStation(genPoint(), foodStationColor, innerCount, innerCount);   // Create FoodStation instance with random aspects.
			System.out.println("FoodStation: " + "X: " + oasis.obtainLocation().getX() + "| Y: " + oasis.obtainLocation().getY());

			mainObjectCollection.add(oasis);  // Add instance of FoodStation to Collection
		}
		
		soundStatus = true; 
		antSpiderSound = new Sound("antSpiderCollisionSound.wav");
		antFoodStationSound = new Sound("antFSCollisionSound.wav");
		antFlagSound = new Sound("antFlagCollisionSound.wav");
		
	}
	
	// Change Ant position near starting Flag. 
	private Point adjustAntPos(Point startingLine) 
	{
		float delta = 23;
		return new Point(startingLine.getX()+delta, startingLine.getY()-delta);
	}

	/** Additional methods here to 
	 *  manipulate world objects and 
	 *  related game state data.      */

	/** genPoint() method generates a random location within the size of the MAP. */
	public Point genPoint()
	{
		int modifiedX = axisXLimit - originX - 100;
		int modifiedY = axisYLimit - originY - 100;
		Random joi = new Random();                                      // Create Random variable to generate random values.
		
		// Generate random number between: MapView Dimensions
		float ranX = (float) ((joi.nextInt(modifiedX)) + originX); 
		float ranY = (float) ((joi.nextInt(modifiedY)) +  originY); 
		
		//System.out.println(" modifiedX: " + modifiedX);
		//System.out.println(" modifiedY: " + modifiedY);
		//System.out.println(" ranX: " + ranX);
		//System.out.println(" ranY: " + ranY);
		
		double roundX = Math.round(ranX * 10.0) / 10.0;                 // Round newly created numbers 
		double roundY = Math.round(ranY * 10.0) / 10.0;                 // Round newly created numbers
		
		Point randomPoint = new Point((float) roundX, (float) roundY);  // Create a Point location based on random numbers.
		return randomPoint;                                             // Return random location Point.
	}
	
	/* Randomly generates size of gameObject. */
	public int genSize()
	{
		Random oni = new Random();    // Random number generator.
		int returnSize = oni.nextInt(15);
		returnSize += 10;
		
		return returnSize;  // return random number within Range: 10-25
	}
	
	/* Randomly generates heading. */
	public int genHeading(int path)
	{
		Random ona = new Random();    // Random number generator
		return ona.nextInt(path);      // return random number
	}
	
	/* Randomly generates speed. */
	public int genSpeed()
	{
		Random onv = new Random();   // Random number generator.
		return onv.nextInt(5) + 5;   // return random number in Range: 5-10
	}
	
	// To record MapView Dimensions for GameState.
	public void recordMapDimensions(int xOrigin, int yOrigin, int xLimit, int yLimit)
	{
		axisXLimit = xLimit; // Record Upper Bounds of MapView
		axisYLimit = yLimit;
		
		originX = xOrigin; // Record Lower Bounds of MapView.
		originY = yOrigin;
	}
	
	// Meant to get each Spider and update the bounds limit. 
	/*public void updateSpiderBounds()
	{
		IIterator storageBox = mainObjectCollection.getIterator();
		GameObject storageItem;
		Spider bogie;
		
		// Find an instance of spider and update the limits of its path. 
		while(storageBox.hasNext())
		{
			storageItem = storageBox.getNext(); // Get GameObject in Collectoin
			if (storageItem instanceof Spider)
			{
				bogie = (Spider) storageItem;
				bogie.mapLimits(axisXLimit, axisYLimit);
			}
		}
		
	}*/
	
	// **** Command Line Methods **** //
	
	//Method to change the rate of acceleration to ant based on health/food.
	public void accerlateAnt()
	{
		int increaseRate = 7, speedIncrease;           // Manages holding onto the speed to ant.
		int foodState   = playerOne.obtainFoodLevel(), // Record Ant food Level
			healthState = playerOne.obtainHealth(),    // Record Ant health Level
			speedState  = playerOne.obtainSpeed(),     // Record Ant speed Level
			speedCap    = playerOne.obtainSpeedCap();  // Record Ant max speed Level
		
		System.out.println("\nAnt Accerlation Applied.");
		
		/* As health and foodLevel decreases, Lower by how much the Ant is to move faster. */
		if ( (healthState == 10) && (foodState >=20 ))
		{
			//Increase the speed of Ant by 7, and check that it doesn't go over speedCap
			speedIncrease = ((speedState + increaseRate) >= speedCap) ? speedCap : (speedState + increaseRate);
			
			//Set newSpeed to Ant's current speed.
			playerOne.adjustSpeed(speedIncrease);
		}
		else if ( (healthState >= 5) && (foodState >=15 ))
		{
			//Change the rate of increase for speed based on health and food level.
			speedIncrease = ((speedState + (increaseRate - 3)) >= speedCap) ? speedCap : (speedState + increaseRate);
			//Set newSpeed to Ant's current speed.
			playerOne.adjustSpeed(speedIncrease);
		}
		else if ( (healthState >= 1) && (foodState >= 1 ))
		{
			//Change the rate of increase for speed based on health and food level.
			speedIncrease = ((speedState + (increaseRate - 6)) >= speedCap) ? speedCap : (speedState + increaseRate - 6);
			//Set newSpeed to Ant's current speed.
			playerOne.adjustSpeed(speedIncrease);
		}
	}
	
	//Implements the Brake Command. Essentially slows speed of Ant.
	public void brakeAnt()
	{
		int brakeOn = 5;                             // Holds rate of braking.
		int antSpeed = playerOne.obtainSpeed();      // Record the speed of Ant.
		
		System.out.println("\nAnt Brake Applied.");  // Prompt User.
		
		//Decrease current speed of Ant
		antSpeed = ((antSpeed - brakeOn ) >= 0) ? (antSpeed - brakeOn ) : 0;
		
		//Record the new Ant Speed.
		playerOne.adjustSpeed(antSpeed);
	}
	
	//Change Ant heading by 5 degrees to the Left or Right.
	public void redirectAnt(char direction)
	{
		int antHeading = playerOne.obtainHeading();      // Obtain the Heading of Ant
		int changeRate = 5, finalHeading = 0;            // Define the change rate, and return heading.
		String left = "Left", right = "Right", turnDesc; // Strings to define which direction to go. 
		
		turnDesc = (direction == 'L') ? left : right;                                  // Check User request. Left or Right?  
		System.out.println("\nAnt Heading Changed to " + turnDesc + " by 5 Degrees."); // Prompt User.
		
		//Change the Heading of Ant based on different commands. L or R
		finalHeading = (direction == 'L') ? (antHeading - changeRate) : 0; 
		finalHeading = (direction == 'R') ? (antHeading + changeRate) : finalHeading;
		
		playerOne.adjustHeading(finalHeading);            // Adjust final heading of Ant.
	}
	
	//Determines and Updates recent flag Ant touched
	public void flagContact(int flagNum)
	{
		int contact = flagNum;       // Convert Char to Int.
		updateFlag = playerOne.obtainRecentFlag();    // Record recent Flag reached by Ant.
		
		System.out.println("\nAnt Made Contact With Flag = " + flagNum + ".");  // Prompt User.
		
		if ((updateFlag + 1) == contact)
		{
			if (soundStatus)
			{
				antFlagSound.playSound(); // Play Sound only when correct Flag reached.
			}
			updateFlag =  (updateFlag + 1);
		}
		
	    playerOne.updateRecentFlag(updateFlag);           //Update recent flag reached by Ant
	    
	    // Notifying Observers of a Change in GameWorld 
	 	this.callObservers();
	 		
	    winCondition(); // Check if Ant has reached final flag.
	}
	
	/**Collision of Ant and FoodStation
	*  1. Fill Ant supply by how much in food station
	*  2. Change color of food station, and generate 
	*     another one randomly 
	 * @param well */
	public void consumeFoodStation(FoodStation well)
	{
		int stationCap;
		currentFoodLevel = playerOne.obtainFoodLevel(); // Record Ant food level.
		System.out.println("\nAnt Made Contact with (Random) FoodStation");  // Prompt User.
		
		// Find a random food station -> Find the first foodStation found.
		// Proceed to have Ant drain food from FoodStation, and change color of food station
		// and make another foodStation.			
		
		stationCap = well.obtainCapacity();               // Obtain how much food is in the station.
		if (stationCap != 0)                                    // Stop loop at first Non-Empty FoodStation
		{
			if (soundStatus)
			{
				antFoodStationSound.playSound();  // Play sound when valid foodStation reached.
			}
			
			playerOne.updateFoodLevel(stationCap + currentFoodLevel); // Update Ant's Food stomach.
			well.fadeFoodStationColor();                  // Change affected foodStation Fade Color
			well.emptyCapacity();                         // Set Capacity to Zero.
			
			stationCap = genSize();                // Generate random size.

			// Creating new Random FoodStation.
			well = new FoodStation(genPoint(), foodStationColor, stationCap, stationCap);
			mainObjectCollection.add(well);  // New collection.
		}
		
	    // Notifying Observers of a Change in GameWorld 
		this.callObservers();
	}
	
	//Spider Collision with Ant
	public void enemyInteraction(char firstP, GameObject otherObject)
	{
		Spider bandit;                       // Spider object to hold colliding spider.
		
		System.out.println("\nAnt Contacted a (Random) Spider.");    //Prompt User
		
		if ((otherObject instanceof Spider) && (firstP == 'A')) // If a Spider was found. 
		{
			if (soundStatus)
			{
				antSpiderSound.playSound();  // Play sound when Ant contacts Spider.
			}
			bandit = (Spider) otherObject;
			playerOne.reduceAntHealth(bandit.obtainDamageAmount());  // Reduce Ant Health by 1.
			playerOne.fadeAntColor();                                // Fade Ant Color a bit as health decreases.
			playerOne.reduceAntSpeed();                              // Reduce Ant Speed by fixed amount.
			playerOne.resetLocPosition();  // Return to Starting line.
		}
		
		// Update Ant health.
		currentHealth = playerOne.obtainHealth();
		
		trueDeath();   // Quit IF Ant has run out of Health.
		
	    // Notifying Observers of a Change in GameWorld 
		this.callObservers();
	}
	
	// Method works to determine if Ant has empty stomach or Zero Health.
	public void trueDeath()
	{
		if ( (currentHealth <= 0) || (currentFoodLevel <= 0 ) )  // If any of these cases occur, reduce lives by 1.
		{
			remainingLives -= 1;     // Sub one from remaining lives

			if (remainingLives != 0) // If not really dead, restart game.
			{
				// Restart the Game
				restartGameLevel();
			}
			else if (remainingLives <= 0)  // If no lives left; end game.
			{
				System.out.println("Total Lives: 0. \nGAME OVER, you failed!");
				System.exit(0); // End the Game
			}
		}
	
	    // Notifying Observers of a Change in GameWorld 
		this.callObservers();
	}
	
	// Restart position of Ant and health after losing a life.
	public void restartGameLevel()
	{
		// Return Ant to starting line w full health, food, position.
		playerOne.setLocation(intialFlag);               // Restart Ant Position
		playerOne.resetHealthFoodLevel();                // Restart Ant Health/Food Level.
		currentFoodLevel = playerOne.obtainFoodLevel();  // Record GameWorld Variable.
	}
	
	// Check if Ant has won the game.
	public void winCondition()
	{
		if (updateFlag == flagLimit)
		{
			System.out.println("GAME OVER: You Win!\nTotal Time: " + clockTimer);
			System.exit(0);
		}
	}
	
	// Method makes GameWorld clock tick, and updates all the gameObjects and their positions.
	public void clockTicker(int tickRate)
	{
		GameObject itemHolder;   // MovableObject holder for loop collection.
		MovableObject creature;
		Spider bogie;
		
		IIterator flipCollection = mainObjectCollection.getIterator();
		
		clockTimer += 1;          // Initiate the TICKING. Increment clock value each time called upon.
		System.out.println("\nThe Clocked has Ticked.");      // Prompt User.

		//for (count = 0; count < collection.size(); count++)   // Loop through collection and update gameObjects.
		while(flipCollection.hasNext())  // While there is something next.
		{			
			//Updates both Spider/Ant locations.
			itemHolder = flipCollection.getNext();
			
			if (itemHolder instanceof MovableObject)
			{	
				/* Move() generates/updates object's location based 
				 * on Heading and Speed. */
				creature = (MovableObject) itemHolder;  // Set GameObject to MovableObject.
				
				creature.move(tickRate);                      // Call MovableObject move method.
				if (creature instanceof Ant)          //Ant's constant food reduction.
				{
					//Reduce Ant's FoodSupply
					((Ant) creature).burnCalories();  //Reduce food supply of Ant.
					currentFoodLevel = playerOne.obtainFoodLevel();  // Keep Updated FoodLevel.
				}
			}
		}
		//Check if Ant has run out of food.
		trueDeath();
	}
	
	// Turn sounds off/on based on paused mode. 
	public void manageSounds(boolean gameOn)
	{
		antSpiderSound.setSoundStatus(gameOn);
		antFoodStationSound.setSoundStatus(gameOn);
		antFlagSound.setSoundStatus(gameOn);
		
		if (gameOn == false) // Game mode to Pause Game.
		{
			// Disable Sounds.
			backgroundMusic.pause();
			soundStatus = false;
		}
		else if (gameOn == true)
		{
			// Enable Sounds
			backgroundMusic.play();
			soundStatus = true;
		}
		this.callObservers();  // Notify so that ScoreView Sound label shows consistent view.
	}
	
	/* Displays important information regarding the GameWorld, such as time, flag reached, and ant lives.*/
	public void displayGameState()
	{
		System.out.println("\nDisplaying GameWorld Information ...");
		System.out.println("Current Lives Remaining:  " + remainingLives);
		System.out.println("Current Clock Time:       " + clockTimer);
		System.out.println("Highest Flag Reached:     " + updateFlag);
		System.out.println("Ant Current Food Level:   " + currentFoodLevel);
		System.out.println("Ant Current Health Level: " + currentHealth);
		System.out.println();
	}
	
	/* Displays information regarding the gameObjects inhabiting the GameWorld ('collection').*/
	public void displayMap()
	{
		IIterator flipCollection = mainObjectCollection.getIterator();
		GameObject itemPull;
		boolean showTitle = true;
		
		while(flipCollection.hasNext())             // Loop to display info on each object in play.
		{
			itemPull = flipCollection.getNext();
			if (itemPull != null)
			{
				if (showTitle)
				{
					System.out.println("\nDisplaying GameWorld Map Information.");  // Prompt User.
					showTitle = false;
				}
				System.out.println(itemPull);
			}
		}
	}
    /* Used by 'x' command to inform user if they really want to quit.
	   By using this method, does the user now have access to 'y'/'n' commands.*/
	public void promptExit()
	{
		gameOver = true;
	}
	
	// Method for when user inputs unknown command key.
	public void badInput()
	{
		System.out.println("\nInvalid Command Input.");
	}
	
	// Terminates the Program
	public void exit()
	{
		if (gameOver)     //If user activated 'x' command, and pressed 'y', then end program.
		{
			System.exit(0);                                // Exits the program. 
		}
		else                                               // If didn't activate 'x' command, user cannot access 'y' command.
		{ 
			System.out.println("\nNeed to Initiate Quit Command ('x') First"
					         + "\nInvalid Command Input.");	
		}
	}
	
	// Method to handle when user decides not to exit the program. If not gone through 'x' command, then its invalid input.
	public void gameOn()
	{
		if (gameOver)                              // If pressed 'x' command then 'n', stop termination of program
		{
			gameOver = false;                      // Set gameOver to false, and return to program
		}
		else                                       // Needs to go through 'x' command.
		{
			System.out.println("\nNeed to Initiate Quit Command ('x') First"
					          + "\nInvalid Command Input.");
		}
	}
	
	// **** ScoreView - GameWorld Reference Methods. ****
	
	public void callObservers()
	{
		//callObservers(); // Call update on info.
		this.setChanged();
		this.notifyObservers();
	}
	
	// Return sound ON/OFF status
	public void setSoundStatus(boolean check)
	{
		soundStatus = check;
		callObservers();
	}

	// Return GameWorld Lives Left.
	public int returnLives()
	{
		return remainingLives;
	}
	// Return GameWorld Time occurred.
	public int returnTime()
	{
		return clockTimer;
	}
	// Return Last Flag touched
	public int returnLastFlag()
	{
		return updateFlag;
	}
	// Return current food level
	public int returnFoodLevel()
	{
		return currentFoodLevel;
	}
	// Return current health level
	public int returnHealthLevel()
	{
		return currentHealth;
	}
	// Return sound ON/OFF status
	public boolean returnSound()
	{
		return soundStatus;
	}
	
	public IIterator returnIterator()
	{
		return mainObjectCollection.getIterator();
	}
	
	public Point obtainMapViewOrigin()
	{
		return new Point(originX, originY);
	}
	
	public boolean obtainPause()
	{
		return gamePaused;
	}
	
	public void setPause(boolean given)
	{
		gamePaused = given;
	}

	// Notify GameWorld Selectable
	public void manageSelectable(boolean gamePaused2) 
	{
		IIterator storage = mainObjectCollection.getIterator();
		GameObject suspect;
		FoodStation oasis;
		
		while(storage.hasNext())
		{
			suspect = storage.getNext();
			if (suspect instanceof FoodStation)
			{
				oasis = (FoodStation) suspect;
				oasis.updateObjectGW(this); // Update current GameWorld info.
				
			}
		}
		
	}
	

	
	
}