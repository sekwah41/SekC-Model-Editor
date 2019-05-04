package com.sekwah.modeleditor.windows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Window extends JFrame {

	private static final long serialVersionUID = -9016687182952886617L;
	
	protected BufferedImage currentTexture = null;
	
	public void setTexture(String texture){
		try {
			currentTexture = ImageIO.read(getClass().getClassLoader().getResource(texture));
		} catch (IOException e) {
			System.out.println("Could not set image: " + texture);
		}
	}
	
	public BufferedImage loadTexture(String texture){
		try {
			return ImageIO.read(getClass().getClassLoader().getResource(texture));
		} catch (IOException e) {
			System.out.println("Could not set image: " + texture);
			return null;
		}
	}
	
}
