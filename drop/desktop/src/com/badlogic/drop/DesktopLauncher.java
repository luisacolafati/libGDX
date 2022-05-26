package com.badlogic.drop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		//display configs:
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Drop Game"); //title of window
		config.setWindowedMode(800, 480); //screen size
		config.useVsync(true);
		config.setForegroundFPS(60);
		
		new Lwjgl3Application(new Drop(), config); //initialize game with display configs
	
	}
}
