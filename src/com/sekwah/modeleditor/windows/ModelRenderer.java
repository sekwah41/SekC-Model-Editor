package com.sekwah.modeleditor.windows;

import com.sekwah.modeleditor.assets.Assets;
import com.sekwah.modeleditor.assets.OpenGlAssets;
import com.sekwah.modeleditor.modelparts.ModelBlock;
import com.sekwah.modeleditor.modelparts.ModelBox;
import com.sekwah.modeleditor.modelparts.ModelRetexturedWithAngleBox;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.lwjgl.opengl.GL11.*;

public class ModelRenderer implements Runnable {

	private Canvas renderCanvas;

	public int currentTextureHeight = 0;

	public int currentTextureWidth = 0;

	public static ArrayList<ModelBox> boxList = new ArrayList<ModelBox>();
	
	public static ArrayList<ModelBlock> enviroBoxList = new ArrayList<ModelBlock>();
	
	public float camRotX = 40;
	
	public float camRotY = 40;
	
	public float camRotZ = 0; // may never be needed

	public float camPosX = 0; // + == right from the character facing forward

	public float camPosY = -10; // + == up from the character

	public float camPosZ = 300; // + == forward from the character

	private static float xPosMoveTo = 0;

	private static float yPosMoveTo = -10;

	private static float zPosMoveTo = 0;

	public static boolean isSlidingCamera = true;

	public float camZoom = 15;
	
	public float currentZoom = 6;

	public static ModelBox selectedBox = null;

	public boolean boxesSelected = true;
	private boolean hasSetMousePos = false;

	public ModelRenderer(Canvas lwjglCanvas) {
		this.renderCanvas = lwjglCanvas;
		
		ModelBox upperArmRight = new ModelRetexturedWithAngleBox(null, "rightArmUpper", 4, 6, 4, -3, -2, -2, 40, 16, 44, 16, 44, 26);
		upperArmRight.setPos(-5F,2F,0);
		boxList.add(upperArmRight);
		
		ModelBox lowerArmRight = new ModelRetexturedWithAngleBox(upperArmRight, "rightArmLower", 4, 6, 4, -2, 0, -2, 40, 22, 44, 26, 48, 16);
		lowerArmRight.setPos(-1F,4F,0);
		upperArmRight.addChild(lowerArmRight);
		
		ModelBox upperArmLeft = new ModelRetexturedWithAngleBox(null, "leftArmUpper", 4, 6, 4, -1, -2, -2, 40, 16, 44, 16, 44, 26);
		upperArmLeft.setPos(5F,2F,0);
		boxList.add(upperArmLeft);
		
		ModelBox lowerArmLeft = new ModelRetexturedWithAngleBox(upperArmLeft, "leftArmLower", 4, 6, 4, -2, 0, -2, 40, 22, 44, 26, 48, 16);
		lowerArmLeft.setPos(1F,4F,0);
		upperArmLeft.addChild(lowerArmLeft);

		/*ModelBox armRight = new ModelBox(null, "rightArm", 4, 12, 4, -3, -2, -2, 40, 16);
		armRight.setPos(-5F,2F,0);
		boxList.add(armRight);

		ModelBox armLeft = new ModelBox(null, "leftArm", 4, 12, 4, -1, -2, -2, 40, 16);
		armLeft.setPos(5F,2F,0);
		boxList.add(armLeft);*/
		
		/*ModelBox legLeft = new ModelBox(null, "leftLeg", 4, 12, 4, -2, -0, -2, 0, 16);
		legLeft.setPos(2F,12F,0);
		boxList.add(legLeft);
		
		ModelBox legRight = new ModelBox(null, "rightLeg", 4, 12, 4, -2, -0, -2, 0, 16);
		legRight.setPos(-2F, 12F, 0);
		legRight.setRotation(0F, 0F, 0F);
		boxList.add(legRight);*/

		// TODO add the proper legs
		ModelBox legLeftUpper = new ModelRetexturedWithAngleBox(null, "leftLegUpper", 4, 6, 4, -2, -0, -2, 0, 16, 4, 16, 4, 24);
		legLeftUpper.setPos(2F,12F,0);
		boxList.add(legLeftUpper);

		ModelBox lowerLegLeft = new ModelRetexturedWithAngleBox(legLeftUpper, "leftLegLower", 4, 6, 4, -2, 0, -2, 0, 22, 4, 24, 8, 16);
		lowerLegLeft.setPos(0F,6F,0);
		legLeftUpper.addChild(lowerLegLeft);

		ModelBox legRightUpper = new ModelRetexturedWithAngleBox(null, "rightLegUpper", 4, 6, 4, -2, -0, -2, 0, 16, 4, 16, 4, 24);
		legRightUpper.setPos(-2F, 12F, 0);
		boxList.add(legRightUpper);

		ModelBox lowerLegRight = new ModelRetexturedWithAngleBox(legRightUpper, "rightLegLower", 4, 6, 4, -2, 0, -2, 0, 22, 4, 24, 8, 16);
		lowerLegRight.setPos(0F,6F,0);
		legRightUpper.addChild(lowerLegRight);


		/*ModelBox body = new ModelBox(null, "torso", 8, 12, 4, -4, 0, -2, 16, 16);
		boxList.add(body);*/

		// TODO stop being lazy and add the proper upper and lower torsos upper and lower part textures
		ModelBox upperBody = new ModelBox(null, "upperBody", 8, 6, 4, -4, 0, -2, 16, 16);
		boxList.add(upperBody);

		ModelBox lowerBody = new ModelBox(null, "lowerBody", 8, 6, 4, -4, 0, -2, 16, 22);
		lowerBody.setPos(0F,6F,0F);
		boxList.add(lowerBody);
		
		ModelBox head = new ModelBox(null, "head", 8, 8, 8, -4, -8, -4, 0, 0);
		
		ModelBox headHelmet = new ModelBox(null, "helmet",8, 8, 8, -4, -8, -4, 32, 0, 0.5F);
		// boxList.add(headHelmet);
		head.addChild(headHelmet);
		boxList.add(head);
		
		/**ModelBlock blockPlanks = new ModelBlock(null,16, 16, 16, -8, 0, -8, 0, 0);
		blockPlanks.setWorldPos(0,0,0);
		enviroBoxList.add(blockPlanks);*/

		ModelBlock blockPlanks = new ModelBlock(null,16, 16, 16, -8, 0, -8, 0, 0);
		blockPlanks.setWorldPos(0,0,0);
		enviroBoxList.add(blockPlanks);
		
		/*for(int x = 0; x < 3; x++){
			for(int z = 0; z < 3; z++){
				ModelBlock blockPlanks = new ModelBlock(null,16, 16, 16, -8, 0, -8, 0, 0);
				blockPlanks.setWorldPos(x - 1,0,z - 1);
				enviroBoxList.add(blockPlanks);
			}
		}*/
	}

	public static void setSelectedBox(ModelBox box) {
		selectedBox = box;
	}

	public static ModelBox getSelectedBox() {
		return selectedBox;
	}

	@Override
	public void run() {
		
		
		Dimension size = null;
		
		size = renderCanvas.getSize();
		try {
			Display.setDisplayMode(new DisplayMode(size.width, size.height));

			Display.setParent(renderCanvas);

			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glEnable(GL_TEXTURE_2D);
		
		int modelTextureID = OpenGlAssets.loadTextureAndGetID(Assets.loadTexture("Images/SEKWAH.png"));
		
		int blockTextureID = OpenGlAssets.loadTextureAndGetID(Assets.loadTexture("Images/Blocks/planks_oak.png"));
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GL11.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		//GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_DEPTH_TEST);

		//glClearAccum(0.0f, 0.0f, 0.0f, 0.0f);

		while (!Display.isCloseRequested())
		{
			size = renderCanvas.getSize();

			//if (size != null)
			//{
				//GL11.glViewport(0, 0, size.width, size.height);
				//GLU.gluPerspective(70, (float)size.width/size.height, 1f, 100);
			//}


			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);


			//glAccum(GL_RETURN, 1.0F);

			//glClear(GL_ACCUM_BUFFER_BIT);
			//glAccum(GL_LOAD, 0.97F);
            //GL11.glClearColor(0, 0, 0, 1);
			
			int mouseWheel = Mouse.getDWheel();

			currentZoom += (camZoom - currentZoom) / 5;

			if(isSlidingCamera){
				if(Math.sqrt(Math.pow((camPosX - xPosMoveTo), 2) + Math.pow((camPosY - yPosMoveTo),2) + Math.pow((camPosZ - zPosMoveTo),2)) > 0.1F){
					camPosX += (xPosMoveTo - camPosX) / 7;
					camPosY += (yPosMoveTo - camPosY) / 7;
					camPosZ += (zPosMoveTo - camPosZ) / 7;
				}
				else{
					isSlidingCamera = false;
				}
			}

			while(Keyboard.next()){
				if(Keyboard.getEventKeyState()){
					// KeyPressed if true
					//if(Keyboard.getEventKey() == Keyboard.KEY_RETURN){

					//}
				}
			}

			if(Mouse.isInsideWindow()){
				int x = Mouse.getX(); // will return the X coordinate on the Display.
				int y = Mouse.getY();

				int dX = 0;
				int dY = 0;

				if(Keyboard.isKeyDown(Keyboard.KEY_A)){
					isSlidingCamera = false;
					float multiplier = (currentZoom + 28F) / 30;
					camPosX += (Math.cos(Math.toRadians(camRotY))) * ((float) 10 / 18) * multiplier;

					camPosZ += (Math.sin(Math.toRadians(camRotY))) * ((float) 10 / 18) * multiplier;
				}

				if(Keyboard.isKeyDown(Keyboard.KEY_D)){
					isSlidingCamera = false;
					float multiplier = -(currentZoom + 28F) / 30;
					camPosX += (Math.cos(Math.toRadians(camRotY))) * ((float) 10 / 18) * multiplier;

					camPosZ += (Math.sin(Math.toRadians(camRotY))) * ((float) 10 / 18) * multiplier;
				}

				if(Keyboard.isKeyDown(Keyboard.KEY_W)){
					isSlidingCamera = false;
					float multiplier = -(currentZoom + 28F) / 30;

					double verty = (Math.cos(Math.toRadians(camRotX + 90))) * ((float) 10 / 18) * multiplier;

					camPosX += (Math.sin(Math.toRadians(camRotX + 90))) *  (Math.cos(Math.toRadians(camRotY + 90))) * ((float) 10 / 18) * multiplier;

					camPosZ += (Math.sin(Math.toRadians(camRotX + 90))) * (Math.sin(Math.toRadians(camRotY + 90))) * ((float) 10 / 18) * multiplier;

					camPosY -= verty;
				}

				if(Keyboard.isKeyDown(Keyboard.KEY_S)){
					isSlidingCamera = false;
					float multiplier = (currentZoom + 28F) / 30;

					double verty = (Math.cos(Math.toRadians(camRotX + 90))) * ((float) 10 / 18) * multiplier;

					camPosX += (Math.sin(Math.toRadians(camRotX + 90))) *  (Math.cos(Math.toRadians(camRotY + 90))) * ((float) 10 / 18) * multiplier;

					camPosZ += (Math.sin(Math.toRadians(camRotX + 90))) * (Math.sin(Math.toRadians(camRotY + 90))) * ((float) 10 / 18) * multiplier;

					camPosY -= verty;
				}

				if(hasSetMousePos) {
					hasSetMousePos = false;
				}
				else{
					dX = Mouse.getDX();
					dY = Mouse.getDY();
				}

				
				camZoom -= mouseWheel / 30;
				
				if(camZoom > 60){
					camZoom = 60;
				}
				else if(camZoom < -20){
					camZoom = -20;
				}

				if(Mouse.isButtonDown(1) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
					isSlidingCamera = false;
					float multiplier = (currentZoom + 28F) / 30;
					camPosX += (Math.cos(Math.toRadians(camRotY))) * ((float) dX / 18) * multiplier;

					camPosZ += (Math.sin(Math.toRadians(camRotY))) * ((float) dX / 18) * multiplier;
					
					double verty = (Math.cos(Math.toRadians(camRotX))) * ((float) dY / 18) * multiplier;

					double vertx = (Math.sin(Math.toRadians(camRotX))) * ((float) dY / 18) * multiplier;
					
					camPosX += (Math.sin(Math.toRadians(camRotX))) *  (Math.cos(Math.toRadians(camRotY + 90))) * ((float) dY / 18) * multiplier;

					camPosZ += (Math.sin(Math.toRadians(camRotX))) * (Math.sin(Math.toRadians(camRotY + 90))) * ((float) dY / 18) * multiplier;
					
					camPosY -= verty;
					
					//camPosx += (float) Mouse.getDX() / 16;

					//camPosy -= (float) Mouse.getDY() / 16;

					if(x == size.width - 1){
						Mouse.setCursorPosition(x - size.width + 4,y);
						hasSetMousePos = true;
					}
					else if(x == 0){
						Mouse.setCursorPosition(x + size.width - 4,y);
						hasSetMousePos = true;
					}

                    if(y == size.height - 1){
                        Mouse.setCursorPosition(x,y - size.height + 4);
                        hasSetMousePos = true;
                    }
                    else if(y == 0){
                        Mouse.setCursorPosition(x,y + size.height - 4);
                        hasSetMousePos = true;
                    }
				}
				else if(Mouse.isButtonDown(1)){
					camRotX -= (float) dY / 4;
					
					camRotY -= (float) dX / 4;

					if(x == size.width - 1){
						Mouse.setCursorPosition(x - size.width + 1,y);
						hasSetMousePos = true;
					}
					else if(x == 0){
						Mouse.setCursorPosition(x + size.width - 1,y);
						hasSetMousePos = true;
					}

                    if(y == size.height - 1){
                        Mouse.setCursorPosition(x,y - size.height + 1);
                        hasSetMousePos = true;
                    }
                    else if(y == 0){
                        Mouse.setCursorPosition(x,y + size.height - 1);
                        hasSetMousePos = true;
                    }
				}
				
				
				
				if(camRotX > 360){
					camRotX -= 360;}
				else if(camRotX < 0){
					camRotX += 360;}
				
				if(camRotY > 360){
					camRotY -= 360;}
				else if(camRotY < 0){
					camRotY += 360;}
			}

			// set the color of the quad (R,G,B,A)
			//GL11.glColor3f(1f,1f,1.0f);
			
			GL11.glColor3f(1f,1f,0.8f);

			/**if(slidingLeft){
				if(xOffset > 1){
					slidingLeft = false;
				}
				xOffset += 0.01F;
				GL11.glTranslatef(0.05F, 0, 0);
			}
			else{
				if(xOffset < -2){
					slidingLeft = true;
				}
				xOffset -= 0.01F;
				GL11.glTranslatef(-0.05F, 0, 0);
			}*/


			GL11.glColor3f(1f,1f,1f);

			GL11.glPushMatrix();

			GLU.gluPerspective(70, (float)size.width/size.height, 1f, 1000);

			GL11.glViewport(0, 0, size.width, size.height);

			//float xOffset = 0;

			//boolean slidingLeft = true;

			GL11.glTranslatef(0, 0F, -31F);

			GL11.glRotatef(180, 1, 0, 0);

			rotateCamera(camRotX,1,0,0,currentZoom);

			rotateCamera(camRotY,0,1,0,currentZoom);

			GL11.glTranslatef(0, 0F, currentZoom);

			GL11.glTranslatef(camPosX, camPosY, camPosZ);

			OpenGlAssets.rebindTexture(blockTextureID);

			for(ModelBlock box: enviroBoxList){
				box.render();
			}

			OpenGlAssets.rebindTexture(modelTextureID);

			/**if(selectedBox != null){
				selectedBox.boxAlpha = 1F;
				selectedBox.render();
			}*/

			for(ModelBox box: boxList){
				if(selectedBox != null){
					box.boxAlpha = 0.4F;
				}
				else{
					box.boxAlpha = 1F;
				}
				box.render();
			}
			
			GL11.glPopMatrix();

            /* Blur which kinda has failed so leave it for now */
            //glAccum(GL_ACCUM, 0.7F);

			/**GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(10, 10);
			GL11.glVertex2f(40, 10);
			GL11.glVertex2f(40, 40);
			GL11.glVertex2f(10, 40);
			GL11.glEnd();*/

			// render OpenGL here
			Display.sync(60);
			Display.update();
		}

		Display.destroy();
	}
	
	private static void rotateCamera(float a, float x, float y, float z, float d) {
		GL11.glTranslatef(0, 0, d);
		
		GL11.glRotatef(a, x, y, z);
		
		GL11.glTranslatef(0, 0, -d);
		
	}
	
	public static boolean isBlockAt(float xPos, float yPos, float zPos){
		for(ModelBlock box: enviroBoxList){
			if(box.xPos == xPos && box.yPos == yPos && box.zPos == zPos){
				return true;
			}
		}
		return false;
	}

	public static void moveCameraTo(float xPos, float yPos, float zPos) {
		xPosMoveTo = -xPos;
		yPosMoveTo = -yPos;
		zPosMoveTo = -zPos;
	}

	public void moveCameraTo(ModelBox box) {
		if(box.parent != null){
			Matrix4f matrix = new Matrix4f();

			ModelBox currentBox = box;
			ArrayList<ModelBox> boxList = new ArrayList<ModelBox>();
			//boxList.add(box);
			while(currentBox.parent != null){
				currentBox = currentBox.parent;
				boxList.add(currentBox);
			}
			Collections.reverse(boxList);
			for(ModelBox box2 : boxList){
				matrix.translate(new Vector3f(box2.xPos, box2.yPos, box2.zPos));

				matrix.rotate((float) Math.toRadians(box2.yRotation), new Vector3f(0, 1, 0));
				matrix.rotate((float) Math.toRadians(box2.zRotation), new Vector3f(0, 0, 1));
				matrix.rotate((float) Math.toRadians(box2.xRotation), new Vector3f(1, 0, 0));
			}
			xPosMoveTo = -(matrix.m00 * box.xPos + matrix.m10 * box.yPos + matrix.m20 * box.zPos + matrix.m30);
			yPosMoveTo = -(matrix.m01 * box.xPos + matrix.m11 * box.yPos + matrix.m21 * box.zPos + matrix.m31);
			zPosMoveTo = -(matrix.m02 * box.xPos + matrix.m12 * box.yPos + matrix.m22 * box.zPos + matrix.m32);


			/*ModelBox currentBox = box;
			GL11.glPushMatrix();
			ArrayList<ModelBox> boxList = new ArrayList<ModelBox>();
			boxList.add(currentBox);
			while(currentBox.parent != null){
				currentBox = currentBox.parent;
				boxList.add(currentBox);
			}
			Collections.reverse(boxList);
			System.out.println(boxList);
			for(ModelBox box2 : boxList){
				GL11.glTranslatef(box2.xPos, box2.yPos, box2.zPos);

				GL11.glRotatef(box2.yRotation, 0, 1, 0);
				GL11.glRotatef(box2.zRotation, 0, 0, 1);
				GL11.glRotatef(box2.xRotation, 1, 0, 0);
			}
			Matrix4f matrix = Assets.getMatrix(GL11.GL_MODELVIEW_MATRIX);
			xPosMoveTo = -(matrix.m00 * box.xPos + matrix.m10 * box.yPos + matrix.m20 * box.zPos);
			yPosMoveTo = -(matrix.m01 * box.xPos + matrix.m11 * box.yPos + matrix.m21 * box.zPos);
			zPosMoveTo = (matrix.m02 * box.xPos + matrix.m12 * box.yPos + matrix.m22 * box.zPos);
			GL11.glPopMatrix();*/
		}
		else{
			xPosMoveTo = -box.xPos;
			yPosMoveTo = -box.yPos;
			zPosMoveTo = -box.zPos;
		}
	}
}
