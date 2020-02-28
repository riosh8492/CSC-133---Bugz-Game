// HECTOR RIOS. ID: 220205545
package com.mycompany.myapp;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

/** Class ScoreView extends Container 
 *  and Implements Observer.
 *  Purpose is mainly to check any 
 *  changes in GameWorld, and to update 
 *  values on score board
 *  Reacts when button(s) is pressed.*/

public class ScoreView extends Container implements Observer
{	
	private GameWorld localGameWorld;                   // Create variable to hold GameWorld Data.
	
	/* Create the many labels to display information of GameWorld onto GUI*/
	private Label livesDesc = new Label("Lives Left:");  
	private Label livesData = new Label();
	private Label clockDesc = new Label("Time:");
	private Label clockData = new Label();
	private Label lastFlagDesc = new Label("Last Flag:");
	private Label lastFLagData = new Label();
	private Label foodLevelDesc = new Label("FoodLevel:");
	private Label foodLevelData = new Label();
	private Label healthLevelDesc = new Label("Health:");
	private Label healthLevelData = new Label();
	private Label soundDesc = new Label("Sound:");
	private Label soundData = new Label();
		
	// Set up Display and customization. 
	public ScoreView(Observable tierra)
	{
		localGameWorld = (GameWorld) tierra;                // Save GameWorld
		this.setLayout( new FlowLayout(Component.CENTER) ); // Set layout

		// Set border color
		this.getUnselectedStyle().setBorder(Border.createLineBorder(2,ColorUtil.BLACK));
		
		renameLabels();   // Update Labels.
		
		tierra.addObserver(this); // Add itself as Observer to GameWorld.
		
		this.getAllStyles().setPaddingBottom(5);
		this.getAllStyles().setPaddingTop(5);
		
		/* ADD Labels to the ScoreView*/
		this.addComponent(livesDesc);
		this.addComponent(livesData);
		this.addComponent(clockDesc);
		this.addComponent(clockData);
		this.addComponent(lastFlagDesc);
		this.addComponent(lastFLagData);
		this.addComponent(foodLevelDesc);
		this.addComponent(foodLevelData);
		this.addComponent(healthLevelDesc);
		this.addComponent(healthLevelData);
		this.addComponent(soundDesc);
		this.addComponent(soundData);
		
	}
	
	// Update gameWorld on GUI/Game
	@Override
	public void update(Observable observable, Object data) {
		// Code here to to output a view based on the data in the Observable. 
        // Code here to update labels from the game/ant state data.
		
		renameLabels(); // Update values with GameWorld values.
		this.repaint();
	}
	
	// Rewrite data labels to scoreView.
	public void renameLabels()
	{
		livesData.setText(String.valueOf(localGameWorld.returnLives()));
		clockData.setText(String.valueOf(localGameWorld.returnTime()));
		lastFLagData.setText(String.valueOf(localGameWorld.returnLastFlag()));
		foodLevelData.setText(String.valueOf(localGameWorld.returnFoodLevel()));
		healthLevelData.setText(String.valueOf(localGameWorld.returnHealthLevel()));
		soundData.setText(( (localGameWorld.returnSound()) ? "ON" : "OFF" ));
	}

}
