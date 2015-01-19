package com.sekwah.modeleditor.assets;

import com.sekwah.modeleditor.windows.MainMenu;
import com.sekwah.modeleditor.windows.ModelEditorWindow;
import com.sekwah.modeleditor.windows.SplashScreen;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Assets {

	public static BufferedImage favicon = null;

	public static ModelEditorWindow modelEditorWindow = null;

	public static ByteBuffer displayFavicon = null;

	public static MainMenu mainMenu = null;

	public static SplashScreen splashScreen = null;

	public static int currentTextureHeight = 0;

	public static int currentTextureWidth = 0;
	
	public static BufferedImage[] Images = new BufferedImage[2];


	public static void loadResources(SplashScreen loadingScreen){
		Assets.splashScreen  = loadingScreen;
		loadingScreen.setProgress("Loading Resources...", 0F);
		favicon = loadTexture("Images/favicon.png");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadingScreen.setProgress("Loading Resources...", 0.2F);
		mainMenu = new MainMenu();
		displayFavicon = Assets.convertToByteBuffer(Assets.favicon);
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadingScreen.setProgress("Loading Resources...", 0.4F);
		modelEditorWindow = new ModelEditorWindow();
		
		loadingScreen.setProgress("Done", 1F);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assets.splashScreen.fadeOut();
		Assets.modelEditorWindow.setVisible(true);
	}

	// http://lwjgl.org/forum/index.php/topic,4083.0.html
	public static BufferedImage loadTexture(String texture){
		try {
			return ImageIO.read(Assets.class.getClassLoader().getResource(texture));
		} catch (IOException e) {
			System.out.println("Could not load image: " + texture);
			return null;
		}
	}

	public static ByteBuffer convertToByteBuffer(BufferedImage image)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "PNG", baos );

			baos.flush();

			byte[] imageInByte = baos.toByteArray();

			baos.close();

			ByteBuffer buffer = ByteBuffer.wrap(imageInByte);

			return buffer; //Generate texture ID
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**public static int loadTextureAndGetID(BufferedImage image)
	{
		//http://www.java-gaming.org/topics/bufferedimage-to-lwjgl-texture/25516/msg/220280/view.html#msg220280

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "PNG", baos );

			baos.flush();

			byte[] imageInByte = baos.toByteArray();

			baos.close();

			ByteBuffer buffer = ByteBuffer.wrap(imageInByte);


		    int textureID = GL11.glGenTextures();
		        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		    // Setup wrap mode
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		    //Setup texture scaling filtering
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		    return textureID; //Generate texture ID
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}*/
	private static final int BYTES_PER_PIXEL = 4;
	public static int loadTextureAndGetID(BufferedImage image){
		glEnable(GL_TEXTURE_2D);

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

		// TODO add code to have the set texture size so if HD textures are used they work.
		currentTextureWidth = image.getWidth();

		currentTextureHeight = image.getHeight();

		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));               // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			}
		}

		buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using 
		// whatever OpenGL method you want, for example:
		int textureID = glGenTextures(); //Generate texture ID
		Images[textureID - 1] = image;
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

		//Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		//Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		//Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		//Return the texture ID so we can bind it later again
		return textureID;
	}

	public static void rebindTexture(int textureID) {
		BufferedImage image = Images[textureID - 1];
		
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

		// TODO add code to have the set texture size so if HD textures are used they work.
		currentTextureWidth = image.getWidth();

		currentTextureHeight = image.getHeight();

		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));               // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			}
		}

		buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
		
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

		//Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	}
}
