package com.sekwah.modeleditor.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class MainMenu extends Window implements MouseListener, MouseMotionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4695980433985614235L;

	protected BufferedImage background = null;
	
	protected BufferedImage logo = null;
	
	protected BufferedImage favicon = null;
	
	private int mouseX = 0;
	private int mouseY = 0;

	private MainMenu mainMenuFrame;
	
	public MainMenu(){
		
		mainMenuFrame = this;
		
		favicon = loadTexture("Images/favicon.png");
		
		this.setUndecorated(true);
		mainMenuFrame.setOpacity(0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(new Color(0, 0, 0, 0));
		
		MainMenuPanel menuPanel = new MainMenuPanel();
		menuPanel.setVisible(false);
		this.setContentPane(menuPanel);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setIconImage(favicon);
		//this.setAlwaysOnTop(true);
		
		this.addMouseMotionListener(this);
	    this.addMouseListener(this);
	    this.setVisible(false);
	    menuPanel.setVisible(true);
	}
	

	@Override
	public void mouseDragged(MouseEvent mouse) {
		this.setLocation((int) mouse.getXOnScreen() - (int) mouseX, (int) mouse.getYOnScreen() - (int) mouseY);
	}

	@Override
	public void mouseMoved(MouseEvent mouse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent mouse) {
		
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
		mouseX = mouse.getX();
		mouseY = mouse.getY();
		
		mouse.getXOnScreen();
		mouse.getYOnScreen();
		
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
		mouseX = 0;
		mouseY = 0;
	}
	
	
	public class MainMenuPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4413911167308156224L;

		public MainMenuPanel() {
			
		    setOpaque(false);
		    setLayout(null);
		    background = loadTexture("Images/mainMenuBackground.png");
		    //logo = loadTexture("Images/modLogo.png");
		    /**CustomButton installButton = new CustomButton("Images/installButton.png", "Images/installButtonOver.png");
		    installButton.onClick(new Runnable(){
		    	public void run(){
		    		
		    	}
		    });
		    installButton.setBounds(305, 240, 340, 60);
		    this.add(installButton);
		    
		    CustomButton infoButton = new CustomButton("Images/infoButton.png", "Images/infoButtonOver.png");
		    infoButton.onClick(new Runnable(){
		    	public void run(){
		    		System.exit(ABORT);
		    	}
		    });
		    infoButton.setBounds(335, 310, 130, 60);
		    this.add(infoButton);
		    
		    CustomButton exitButton = new CustomButton("Images/exitButton.png", "Images/exitButtonOver.png");
		    exitButton.onClick(new Runnable(){
		    	private float fade = 1.0F;
		    	private int delta = 0;
		    	private long lastTime;
				public void run(){
		    		
		    		lastTime = System.nanoTime();;
		    		
		    		while(true){
		    			double nsPerTick = 1000000000D / 120D;
			            
			            delta = 0;
			            
			            long now = System.nanoTime();
						delta += (now - lastTime) / nsPerTick;
			            if(delta >= 1){
			            	lastTime = now;
			            }
			            else if(delta < 0){
			            	lastTime = now;
			            	System.out.println("[Naruto Mod] Your computer seems to have traveled back in time :O");
			            }
			            if(fade <= 1 && fade >= 0){
			            	mainMenuFrame.setOpacity(fade);
			            }
			            fade -= (float) (delta) / 20;
			            
			            if(fade <= 0){
			            	System.exit(ABORT);
			            }
		    		}
		    	}
		    });
		    exitButton.setBounds(485, 310, 130, 60);
		    this.add(exitButton);*/
		}

		@Override
		public Dimension getPreferredSize() {
		    return new Dimension(500, 500);
		}

		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
		    Graphics2D g2d = (Graphics2D) g.create();
		    RenderingHints hints = new RenderingHints(
		            RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ANTIALIAS_ON);
		    g2d.setRenderingHints(hints);
		    g2d.setColor(getBackground());
		    g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		    g2d.drawImage(logo, 280, 90, 450, 152, this);
		    g2d.setColor(new Color(0,0,0));
		    //g2d.fillRect(315, 243, 330, 60);
		    g2d.dispose();
		}
	}
	
	public class CustomButton extends JPanel implements MouseListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3484619956861551476L;
		private BufferedImage buttonImage = null;
		private BufferedImage buttonImageOver = null;
		
		private BufferedImage buttonCurrentImage = null;
		
		private Runnable clickEvent = null;

		public CustomButton(String imageLocation, String imageLocationOver) {
			setOpaque(false);
			buttonImage = loadTexture(imageLocation);
			buttonImageOver = loadTexture(imageLocationOver);
			
			buttonCurrentImage = buttonImage;
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.addMouseListener(this);
		}

		public void onClick(Runnable runnable) {
			clickEvent = runnable;
			
		}

		@Override
		protected void paintComponent(Graphics g) {
			//super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
			Graphics2D g2d = (Graphics2D) g.create();
			RenderingHints hints = new RenderingHints(
					RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHints(hints);
			g2d.setColor(getBackground());
			g2d.drawImage(buttonCurrentImage, 0, 0, getWidth(), getHeight(), this);
			g2d.dispose();
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Thread clickThread = new Thread(clickEvent);
			clickThread.start();
			//clickEvent.run();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			buttonCurrentImage = buttonImageOver;
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			buttonCurrentImage = buttonImage;
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public void fadeIn() {
		this.setVisible(true);
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
			            	System.out.println("[Naruto Mod] Your computer seems to have traveled back in time :O");
			            }
			            if(fade <= 1 && fade >= 0){
			            	mainMenuFrame.setOpacity(fade);
			            }
			            fade += (float) (delta) / 20;
			            
			            if(fade >= 1){
			            	mainMenuFrame.setOpacity(1);
			            	fading = false;
			            }
		    		}
		    	}
		    });
		fadeIn.run();
	}
}
