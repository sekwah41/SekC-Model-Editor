package com.sekwah.modeleditor.windows;

import com.sekwah.modeleditor.assets.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SplashScreen extends Window {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4641214802691288176L;

	protected BufferedImage splashImage = null;
	
	protected BufferedImage favicon = null;

	private SplashScreen splashScreenFrame;
	
	public String loadingProgress = "Loading Resources...";
	
	public float loadingPercent = 0F;
	
	
	public SplashScreen(){
		
		splashScreenFrame = this;
		
		favicon = loadTexture("Images/favicon.png");
		
		this.setUndecorated(true);
		splashScreenFrame.setOpacity(0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(new Color(0, 0, 0, 0));
		this.setContentPane(new SplashLogo());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setIconImage(favicon);
		//this.setAlwaysOnTop(true);
		
		this.setProgress("Initialising", 0F);
	}


	public class SplashLogo extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1845398991637298683L;

		public SplashLogo() {
			setOpaque(false);
			setLayout(new GridBagLayout());
			splashImage = loadTexture("Images/splashImage.png");
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(711, 400);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
			Graphics2D g2d = (Graphics2D) g.create();
			RenderingHints hints = new RenderingHints(
					RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHints(hints);
			g2d.setColor(getBackground());
			g2d.drawImage(splashImage, 0, 0, getWidth(), getHeight(), this);
			g2d.setColor(new Color(10,160,200));
			g2d.fillRect(274, 164, (int) (318 * loadingPercent), 2);
			g2d.setColor(new Color(0,0,0));
			g2d.drawString(loadingProgress, 279, 180);
			g2d.dispose();
		}
	}


	public void fadeIn() {
		final SplashScreen splashScreen = this;
		Thread fadeIn = new Thread(new Runnable(){
		    	private float fade = 0.0F;
		    	private int delta = 0;
		    	private long lastTime;
				public void run(){
		    		
		    		lastTime = System.nanoTime();
		    		
		    		boolean fading = true;
		    		
		    		while(fading){
		    			double nsPerTick = 1000000000D / 120D;
			            
			            delta = 0;
			            
			            long now = System.nanoTime();
						delta += (now - lastTime) / nsPerTick;
			            if(delta >= 1){
			            	lastTime = now;
			            }
			            else if(delta < 0){
			            	lastTime = now;
			            	System.out.println("Your computer seems to have traveled back in time :O");
			            }
			            if(fade <= 1 && fade >= 0){
			            	splashScreenFrame.setOpacity(fade);
			            }
			            fade += (float) (delta) / 70;
			            
			            if(fade >= 1){
			            	splashScreenFrame.setOpacity(1);
			            	fading = false;
			            	
			            	Assets.loadResources(splashScreen);
			            }
		    		}
		    	}
		    });
		fadeIn.run();
	}

	public void fadeOutNohide() {
		Thread fadeOut = new Thread(new Runnable(){
			private float fade = 1.0F;
			private int delta = 0;
			private long lastTime;
			public void run(){

				lastTime = System.nanoTime();

				boolean fading = true;

				while(fading){
					double nsPerTick = 1000000000D / 120D;

					delta = 0;

					long now = System.nanoTime();
					delta += (now - lastTime) / nsPerTick;
					if(delta >= 1){
						lastTime = now;
					}
					else if(delta < 0){
						lastTime = now;
						System.out.println("Your computer seems to have traveled back in time :O");
					}
					if(fade <= 1 && fade >= 0){
						splashScreenFrame.setOpacity(fade);
					}
					fade -= (float) (delta) / 20;

					if(fade <= 0){
						splashScreenFrame.setOpacity(0);
						fading = false;
					}
				}
			}
		});
		fadeOut.run();
	}

	public void fadeOut() {
		Thread fadeOut = new Thread(new Runnable(){
			private float fade = 1.0F;
			private int delta = 0;
			private long lastTime;
			public void run(){

				lastTime = System.nanoTime();

				boolean fading = true;

				while(fading){
					double nsPerTick = 1000000000D / 120D;

					delta = 0;

					long now = System.nanoTime();
					delta += (now - lastTime) / nsPerTick;
					if(delta >= 1){
						lastTime = now;
					}
					else if(delta < 0){
						lastTime = now;
						System.out.println("Your computer seems to have traveled back in time :O");
					}
					if(fade <= 1 && fade >= 0){
						splashScreenFrame.setOpacity(fade);
					}
					fade -= (float) (delta) / 20;

					if(fade <= 0){
						splashScreenFrame.setOpacity(0);
						fading = false;
						splashScreenFrame.setVisible(false);
					}
				}
			}
		});
		fadeOut.run();
	}


	public void setProgress(String progressText, float progress) {
		this.loadingProgress = progressText + " " + (int) (progress * 100) + "%";
		this.loadingPercent = progress;
		this.repaint();
	}
}
