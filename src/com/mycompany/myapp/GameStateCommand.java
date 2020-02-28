package com.mycompany.myapp;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class GameStateCommand extends Command
{
	private String gameState;
	public GameStateCommand(String command) // First State: "Play", but Button display should be PAUSE.
	{
		super(command);
		gameState = command;
	}
	
	// Return GameState.
	public String currentGameState()
	{
		return gameState;
	}
	
	//  
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("Pressed Southern Button.");
		if (gameState == "Play")
		{
			gameState = "Pause";
		}
		else if (gameState == "Pause")
		{
			gameState = "Play";
		}
		this.setCommandName(gameState);
	}
}
