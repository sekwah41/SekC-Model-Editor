package com.sekwah.modeleditor;

import com.sekwah.modeleditor.windows.SplashScreen;

public class SekCEditor {

	public static void main(String[] args){
		
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
