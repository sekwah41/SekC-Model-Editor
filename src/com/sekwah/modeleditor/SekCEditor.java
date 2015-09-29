package com.sekwah.modeleditor;

import com.sekwah.modeleditor.windows.SplashScreen;

import java.io.File;

public class SekCEditor {

	public static void main(String[] args){
		System.setProperty("java.library.path", new File("libs").getAbsolutePath());
		
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		
		SplashScreen splashScreen = new SplashScreen();
		splashScreen.setOpacity(0);
		splashScreen.setVisible(true);
		splashScreen.fadeIn();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
