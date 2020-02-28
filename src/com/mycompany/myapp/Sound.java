package com.mycompany.myapp;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound 
{
	Media soundBit;
	boolean triggerOn = true;
	public Sound(String filename)
	{
		try
		{
			InputStream is = Display.getInstance().getResourceAsStream(getClass(),  ("/" + filename));
			soundBit = MediaManager.createMedia(is, "audio/wav");
			//System.out.println("Sound pass yes");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//System.out.println("Sound pass NO");
		}
	}
	
	// This plays the sounds encapsulated.
	public void playSound()
	{
		if (triggerOn)
		{
			soundBit.setTime(0);
			soundBit.play();	
		}
	}
	
	public void setSoundStatus(boolean input)
	{
		triggerOn = input;
	}
}

