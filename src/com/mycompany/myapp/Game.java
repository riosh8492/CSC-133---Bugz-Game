// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;

/** Class Game first introduces a gameWorld where all gameObjects 
 * will be located, and calls upon a private method, play(), that prompts
 * the user to enter commands to simulate certain aspects regarding 
 * the game. Certain things like (PRETEND) collision, acceleration of objects, 
 * and displaying of Map information. Game class inherits from Form class.
 * */

public class Game extends Form implements Runnable, ActionListener
{
	private UITimer endTicker; // Timer for Game.
	private GameObjectCollection gameObjectCollection;
	private int tickRate = 500;
	private boolean gamePaused = false;  // false = NOT in pause mode
	private GameWorld gw;                  // GameWorld variable to hold gameObjects.
    private MapView mv;                    // MapView variable
	private ScoreView sv;                  // ScoreView variable
	
	private WesternContainer  wBox; 	   // West Container.
	private EasternContainer  eBox; 	   // East Container.
	private SouthernContainer sBox; 	   // South Container.
	private Toolbar           mTitleBar;   // Toolbar w/ menu items. 
	
	private AccelerateCommand aCommand;            // Command: Accelerate
	private LeftCommand lCommand;                  // Command: Left Turn
	private BreakCommand bCommand;                 // Command: Brake
	private RightCommand rCommand;                 // Command: Right Turn
	private AboutGame abCommand;				   // Command: About
	private ExitCommand xCommand;                  // Command: Exit
	private HelpCommand hCommand;				   // Command: Help
	private GameStateCommand gsCommand;  // GameState Command.
	private PositionCommand pCommand;
	
	private BellButton manageGameButton;
	private BellButton selectButton;
	private BellButton accelButton;
	private BellButton leftButton;
	private BellButton breakButton;
	private BellButton rightButton;
	private SoundCheck soundCheck;
	
	/** Game Constructor creates a new GameWorld, and calls method
	  * to initialize map with gameObjects. Play() is called have 
	  * user enter commands to affect objects in gameWorld.*/
	public Game() 
	{
		// Implementing Timer for Game Class. 
		endTicker = new UITimer(this);
		endTicker.schedule(tickRate, true, this);
				
		gw = new GameWorld();   // Create "Observable" GameWorld
		mv = new MapView(gw);   // Create an "Observer" for the map, and Register Map Observer.
		sv = new ScoreView(gw); // Create an "Observer" for the game/ant state data, and Register Score Observer.
		
		wBox = new WesternContainer(); // Initialize Containers WEST, SOUTH, EAST 
		eBox = new EasternContainer();
		sBox = new SouthernContainer();
		mTitleBar = new Toolbar();                   // Create Tool-bar.
		soundCheck = new SoundCheck(gw);  // Create coundCheck
		
		/** Code here to create Command objects for each command.
		 *  Add commands to side menu and title bar area, bind commands to keys,
		 *  create control containers for the buttons, add buttons to the control 
		 *  containers, add commands to the buttons, add control containers, 
		 *  MapView, and ScoreView to the form. */
				
		/* Creation of Command Objects */
	    aCommand = new AccelerateCommand("Accelerate", gw);
	    lCommand = new LeftCommand("Left", gw);
	    bCommand = new BreakCommand("Break", gw);
	    rCommand = new RightCommand("Right", gw);
	    abCommand = new AboutGame(); 
	    xCommand = new ExitCommand("Exit", gw);
	    hCommand = new HelpCommand("Help", gw);
	    gsCommand = new GameStateCommand("Pause");
	    pCommand = new PositionCommand("Position");
	    
	    // Tool-bar variables 
	    Label myTitle = new Label("BUGZ GAME");	    
	    
		/* Setting Layout for Game. */
		this.setLayout( new BorderLayout() );
		
		/* Creation of Buttons */
		accelButton = new BellButton("Accelerate");  // Create Button for Accelerate Command
		accelButton.setCommand(aCommand);                       // Set button to correct Command
		this.addKeyListener('a', aCommand);						// Add key listener a to command
		
		leftButton = new BellButton("Left");    // Create Button for Left Turning Command
		leftButton.setCommand(lCommand);				   // Set button to correct Command
		this.addKeyListener('l', lCommand);                // Add key listener l to command
		
		breakButton = new BellButton("Break");
		breakButton.setCommand(bCommand);				   // Set button to correct Command
		this.addKeyListener('b', bCommand);  			   // Add key listener b to command

		rightButton = new BellButton("Right");
		rightButton.setCommand(rCommand);			       // Set button to correct Command
		this.addKeyListener('r', rCommand);				   // Add key listener r to command
		
		manageGameButton = new BellButton("Pause");
		manageGameButton.setCommand(gsCommand);
		manageGameButton.addActionListener(this);
		
		selectButton = new BellButton("Position");
		selectButton.setCommand(pCommand);
		selectButton.addActionListener(mv);
		
		
		System.out.println("Game World Information Display-> DISABLED");
		//gw.displayMap();
		
		// Creation/Modification of Tool-bar.
		this.setToolbar(mTitleBar);               // Set previous tool-bar to GUI
		mTitleBar.setTitleComponent(myTitle);     // Set Title of tool-bar.
		Toolbar.setOnTopSideMenu(false);	      // Sets Side Menu to the Right
		
		mTitleBar.addCommandToSideMenu(aCommand);      // Adding A Command to SideMenu
		mTitleBar.addComponentToSideMenu(soundCheck);  // Adding Sound CheckBox to SideMenu
		mTitleBar.addCommandToSideMenu(abCommand);     // Command to side bar menu.
		mTitleBar.addCommandToSideMenu(xCommand);      // Command to side bar menu.
		mTitleBar.addCommandToRightBar(hCommand);      // Add Help info Command to the Right.
		soundCheck.addActionListener(soundCheck);      // Set SountCheck as its own Listener.
		

		/* Adding layouts to MapView, and ScoreView*/
		this.add(BorderLayout.CENTER, mv);
		this.add(BorderLayout.NORTH, sv);
		
		wBox.add(accelButton);             // Add Accelerate Command to WEST container
		wBox.add(leftButton);              // Add Left Command to WEST container
		this.add(BorderLayout.WEST, wBox); // Set layout of WEST container.
		
		eBox.add(breakButton);             // Add Break Command to EAST container
		eBox.add(rightButton);             // Add Right Command to EAST container
		this.add(BorderLayout.EAST, eBox); // Set Layout of EAST container.
		
		sBox.add(selectButton);
		sBox.add(manageGameButton);           // Add Play/Pause Command to SOUTH container.
		this.add(BorderLayout.SOUTH, sBox);  // Set layout of SOUTH container.
			
		this.show();
		
		gw.recordMapDimensions(mv.getAbsoluteX(), mv.getAbsoluteY(), mv.getWidth(), mv.getHeight());
		System.out.println("MapView Origin: " + "X: " + mv.getAbsoluteX() + " | " + "Y: " + mv.getAbsoluteY());
		System.out.println("GAMEWORLD LIMIT: x: " + mv.getWidth() + " / y: " + mv.getHeight());
		gw.init();     // Initialize world.
	}

	// To be called by the Timer in GameClass. 
	@Override
	public void run() 
	{
		IIterator initalFinder = gw.returnIterator();
		IIterator innerFinder;
		
		GameObject storageObject, innerObject;
		FoodStation supplyPost;                         // Holder for FoodStations usage.
		Spider bogie;
		Ant playerOne;
		
		gw.clockTicker(tickRate); // Calls the move method of each GameObject.
		while(initalFinder.hasNext())
		{
			storageObject = (GameObject) initalFinder.getNext();  // Get next GameObject.
			
			innerFinder = gw.returnIterator();  // Create a second Iterator to check collisions
			
			while(innerFinder.hasNext())
			{
				innerObject = (GameObject) innerFinder.getNext();    // Get next Object in Collection.
				if (storageObject != innerObject)                    // Ignore if both objects the same.
				{
					storageObject.updateObjectGW(gw);            // Update object's most recent gameWorld instance. 
					if (storageObject.collidesWith(innerObject))     // Check if there was collision
					{
						storageObject.handleCollision(innerObject);  // Call collision handler.
						
						storageObject.addCollisionObject(innerObject); // Add innerObject to list of Recorded Collisions in StorObj.
						innerObject.addCollisionObject(storageObject); // Add storageObject to list of Recorded Collisions in innerObj
					}
					else if (storageObject.containsCollisionObject(innerObject))
					{
						if (!storageObject.collidesWith(innerObject)) // If not colliding anymore. 
						{
							storageObject.removeCollisionObject(innerObject);// Remove innerObject to list of Recorded Collisions in StorObj.
							innerObject.removeCollisionObject(storageObject); // Remove storageObject to list of Recorded Collisions in innerObj
						}
					}
				}
				else if (storageObject instanceof Spider) // Handle out of bound Spiders.
				{
					bogie = (Spider) storageObject;
					bogie.watchEnemyBounds(mv.getAbsoluteX(), mv.getAbsoluteY());       // Check if Spider is going out of bounds.
				}
				else if (storageObject instanceof Ant) // Handle out of bounds Ant.
				{
					playerOne = (Ant) storageObject;
					playerOne.watchBounds(mv.getAbsoluteX(), mv.getAbsoluteY(), mv.getWidth(), mv.getHeight());
				}
			}
		}
		mv.repaint(); // Call repaint to Update drawings of MapView.
	}
	
	// Manipulate Game Object Movement.
	public void manageAnimations(boolean gameOn)
	{
		if (gameOn == false) // Means to Mode: Pause the Game.
		{
			endTicker.cancel(); // Stop the Game Clock.
		}
		else if (gameOn == true)
		{
			endTicker.schedule(tickRate, true, this);  // Resume the ClockTicker.
		}
	}
	

	// This is meant to listen when the button is pressed. 
	@Override
	public void actionPerformed(ActionEvent evt) 
	{
		System.out.println("Pause/Play Pressed From GAME.");
		// Task 1 -> Change name, and maybe color? 
		manageGameButton.updateBellButton();
		// Task 2 -> Pause Mode: Animation Stops - Objects Dont Move. 
		//           Clock Stops, Commands disabled. All Sounds Stop, 
		// Entering Pause Mode -> disable stuff
		if ((gsCommand.getCommandName() != "Pause") && gamePaused == false)
		{
			System.out.println("test 1: Mode - Play -> Pause | Display - Pause -> Play");
			gamePaused = true;
			gw.setPause(true);
			
			this.setGameButtons(false);   // Disables Buttons.
			this.manageAnimations(false); // Disable the Game Clock.
			gw.manageSounds(false);       // Disable Sounds
			
			// Task 1: find and call Selectable objects.
			//gw.manageSelectable(gamePaused);
			
		}   // Entering Play Mode -> ENABLE stuff
		else if ((gsCommand.getCommandName() != "Play") && gamePaused == true)
		{
			System.out.println("test 2: Mode - Pause -> Play | Display - Play -> Pause");
			gamePaused = false;  
			gw.setPause(false);
			setGameButtons(true);        // Enable the Game Buttons
			this.manageAnimations(true); // Enable the Game Clock.
			gw.manageSounds(true);       // Enable Sounds

		}
	}	
	
	// Disables all buttons except for Play/Pause and Position.
	public void setGameButtons(boolean gameOn)
	{
		accelButton.setEnabled(gameOn); // Disable/enable buttons
		leftButton.setEnabled(gameOn);
		rightButton.setEnabled(gameOn);
		breakButton.setEnabled(gameOn);
		
		//Disables the Side menu Items.
		aCommand.setEnabled(gameOn);
		soundCheck.setEnabled(gameOn);     // Adding Sound CheckBox to SideMenu
		abCommand.setEnabled(gameOn);      // Command to side bar menu.
		xCommand.setEnabled(gameOn);       // Command to side bar menu.
		hCommand.setEnabled(gameOn);       // Add Help info Command to the Right.
		soundCheck.setEnabled(gameOn);     // Set SountCheck as its own Listener.
		
		
		//accelButton leftButton breakButton rightButton;
		if (gameOn == false)
		{
			this.removeKeyListener('a', aCommand);	// Disable KeyListeners
			this.removeKeyListener('l', lCommand);
			this.removeKeyListener('b', bCommand);
			this.removeKeyListener('r', rCommand);
			accelButton.disableButtonView();
			leftButton.disableButtonView();
			breakButton.disableButtonView();
			rightButton.disableButtonView();

		}
		else if (gameOn)
		{
			System.out.println("ENABLE BUTTONS");
			this.addKeyListener('a', aCommand);	  // ENABLE KeyListeners
			this.addKeyListener('l', lCommand);
			this.addKeyListener('b', bCommand);
			this.addKeyListener('r', rCommand);
			
			accelButton.enableButtonView();
			leftButton.enableButtonView();
			breakButton.enableButtonView();
			rightButton.enableButtonView();
		}
	}
	
	
}