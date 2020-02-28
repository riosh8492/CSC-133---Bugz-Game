package com.mycompany.myapp;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class BGSound implements Runnable, ActionListener
{
	private Media loopSound;
	
    public BGSound(String fileName)
    { 
    	try
    	{
    		//System.out.println("Entered?");
    		InputStream is = Display.getInstance().getResourceAsStream(getClass(), ("/"+fileName));
    		//attach a runnable to run when media has finished playing //as the last parameter
    		//System.out.println("Middle?");

    		loopSound = MediaManager.createMedia(is, "audio/wav", this);
    		//System.out.println("End?");
    	}
		catch(Exception e)
		{
		  e.printStackTrace();
		  //System.out.println("Sound pass No");
		}
    }
    
    //pause playing the sound
	public void pause()
	{ 
		loopSound.pause();
	} 
	
	//continue playing from where we have left off
	public void play()
	{ 
		loopSound.play();
	} 
	
	//entered when media has finished playing
	public void run() 
	{
		//start playing from time zero (beginning of the sound file)
		loopSound.setTime(0);
		loopSound.play();
	}

	@Override
	public void actionPerformed(ActionEvent evt) 
	{
		// TODO Auto-generated method stub
		
	}
}
