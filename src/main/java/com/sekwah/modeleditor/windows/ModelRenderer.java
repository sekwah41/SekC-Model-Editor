package com.sekwah.modeleditor.windows;

import com.sekwah.modeleditor.assets.Assets;
import com.sekwah.modeleditor.assets.OpenGlAssets;
import com.sekwah.modeleditor.modelparts.ModelBlock;
import com.sekwah.modeleditor.modelparts.ModelBox;
import com.sekwah.modeleditor.modelparts.ModelRetexturedWithAngleBox;
import com.sekwah.modeleditor.viewer.Camera;
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

	/*public float camRotX = 40;

	public float camRotY = 40;

	public float camRotZ = 0; // may never be needed

	public float camPosX = 0; // + == right from the character facing forward

	public float camPosY = -10; // + == up from the character

	public float camPosZ = 300; // + == forward from the character*/

	public Camera camera = new Camera(0,-10,300,40,40);

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
		ModelBox upperBody = new ModelRetexturedWithAngleBox(null, "upperBody", 8, 6, 4, -4, 0, -2, 16, 16, 20, 16, 28,24);
		boxList.add(upperBody);

		ModelBox lowerBody = new ModelRetexturedWithAngleBox(null, "lowerBody", 8, 6, 4, -4, 0, -2, 16, 22, 28, 24, 28,16);
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

		glEnable(GL_TEXTURE_2D);

		int modelTextureID = OpenGlAssets.loadTextureAndGetID(Assets.loadTexture("Images/SEKWAH.png"));

		int blockTextureID = OpenGlAssets.loadTextureAndGetID(Assets.loadTexture("Images/Blocks/planks_oak.png"));

		// TODO rewrite camera transformations and stuff properly using the projection and modelview matricies
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		//glOrtho(0, 800, 0, 600, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_DEPTH_TEST);
		glLoadIdentity();

		//glClearAccum(0.0f, 0.0f, 0.0f, 0.0f);

		while (!Display.isCloseRequested())
		{
			size = renderCanvas.getSize();

			//if (size != null)
			//{
				//glViewport(0, 0, size.width, size.height);
				//GLU.gluPerspective(70, (float)size.width/size.height, 1f, 100);
			//}

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			//glAccum(GL_RETURN, 1.0F);

			//glClear(GL_ACCUM_BUFFER_BIT);
			//glAccum(GL_LOAD, 0.97F);
            //glClearColor(0, 0, 0, 1);

			int mouseWheel = Mouse.getDWheel();

			currentZoom += (camZoom - currentZoom) / 5;

			if(isSlidingCamera){
				if(Math.sqrt(Math.pow((camera.posX - xPosMoveTo), 2) + Math.pow((camera.posY - yPosMoveTo),2) + Math.pow((camera.posZ - zPosMoveTo),2)) > 0.1F){
					camera.posX += (xPosMoveTo - camera.posX) / 7;
					camera.posY += (yPosMoveTo - camera.posY) / 7;
					camera.posZ += (zPosMoveTo - camera.posZ) / 7;
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
					camera.posX += (Math.cos(Math.toRadians(camera.rotY))) * ((float) 10 / 18) * multiplier;

					camera.posZ += (Math.sin(Math.toRadians(camera.rotY))) * ((float) 10 / 18) * multiplier;
				}

				if(Keyboard.isKeyDown(Keyboard.KEY_D)){
					isSlidingCamera = false;
					float multiplier = -(currentZoom + 28F) / 30;
					camera.posX += (Math.cos(Math.toRadians(camera.rotY))) * ((float) 10 / 18) * multiplier;

					camera.posZ += (Math.sin(Math.toRadians(camera.rotY))) * ((float) 10 / 18) * multiplier;
				}

				if(Keyboard.isKeyDown(Keyboard.KEY_W)){
					isSlidingCamera = false;
					float multiplier = -(currentZoom + 28F) / 30;

					double verty = (Math.cos(Math.toRadians(camera.rotX + 90))) * ((float) 10 / 18) * multiplier;

					camera.posX += (Math.sin(Math.toRadians(camera.rotX + 90))) *  (Math.cos(Math.toRadians(camera.rotY + 90))) * ((float) 10 / 18) * multiplier;

					camera.posZ += (Math.sin(Math.toRadians(camera.rotX + 90))) * (Math.sin(Math.toRadians(camera.rotY + 90))) * ((float) 10 / 18) * multiplier;

					camera.posY -= verty;
				}

				if(Keyboard.isKeyDown(Keyboard.KEY_S)){
					isSlidingCamera = false;
					float multiplier = (currentZoom + 28F) / 30;

					double verty = (Math.cos(Math.toRadians(camera.rotX + 90))) * ((float) 10 / 18) * multiplier;

					camera.posX += (Math.sin(Math.toRadians(camera.rotX + 90))) *  (Math.cos(Math.toRadians(camera.rotY + 90))) * ((float) 10 / 18) * multiplier;

					camera.posZ += (Math.sin(Math.toRadians(camera.rotX + 90))) * (Math.sin(Math.toRadians(camera.rotY + 90))) * ((float) 10 / 18) * multiplier;

					camera.posY -= verty;
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
					camera.posX += (Math.cos(Math.toRadians(camera.rotY))) * ((float) dX / 18) * multiplier;

					camera.posZ += (Math.sin(Math.toRadians(camera.rotY))) * ((float) dX / 18) * multiplier;

					double verty = (Math.cos(Math.toRadians(camera.rotX))) * ((float) dY / 18) * multiplier;

					double vertx = (Math.sin(Math.toRadians(camera.rotX))) * ((float) dY / 18) * multiplier;

					camera.posX += (Math.sin(Math.toRadians(camera.rotX))) *  (Math.cos(Math.toRadians(camera.rotY + 90))) * ((float) dY / 18) * multiplier;

					camera.posZ += (Math.sin(Math.toRadians(camera.rotX))) * (Math.sin(Math.toRadians(camera.rotY + 90))) * ((float) dY / 18) * multiplier;

					camera.posY -= verty;

					//camera.posx += (float) Mouse.getDX() / 16;

					//camera.posy -= (float) Mouse.getDY() / 16;

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
					camera.rotX -= (float) dY / 4;

					camera.rotY -= (float) dX / 4;

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



				if(camera.rotX > 360){
					camera.rotX -= 360;}
				else if(camera.rotX < 0){
					camera.rotX += 360;}

				if(camera.rotY > 360){
					camera.rotY -= 360;}
				else if(camera.rotY < 0){
					camera.rotY += 360;}
			}

			// set the color of the quad (R,G,B,A)
			//glColor3f(1f,1f,1.0f);

			glColor3f(1f, 1f, 0.8f);

			/**if(slidingLeft){
				if(xOffset > 1){
					slidingLeft = false;
				}
				xOffset += 0.01F;
				glTranslatef(0.05F, 0, 0);
			}
			else{
				if(xOffset < -2){
					slidingLeft = true;
				}
				xOffset -= 0.01F;
				glTranslatef(-0.05F, 0, 0);
			}*/


			glColor3f(1f,1f,1f);

			glPushMatrix();

			glMatrixMode(GL11.GL_PROJECTION);
			glLoadIdentity();

			GLU.gluPerspective(70, (float)size.width/size.height, 1f, 1000);

			glViewport(0, 0, size.width, size.height);

			//float xOffset = 0;

			//boolean slidingLeft = true;

			glTranslatef(0, 0F, -31F);

			glRotatef(180, 1, 0, 0);

			rotateCamera(camera.rotX, 1, 0, 0, currentZoom);

			rotateCamera(camera.rotY,0,1,0,currentZoom);

			glTranslatef(0, 0F, currentZoom);

			glTranslatef(camera.posX, camera.posY, camera.posZ);

			glMatrixMode(GL11.GL_MODELVIEW);
			glLoadIdentity();

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


			glPopMatrix();

            /* Blur which kinda has failed so leave it for now */
            //glAccum(GL_ACCUM, 0.7F);

			/**glBegin(GL_QUADS);
			glVertex2f(10, 10);
			glVertex2f(40, 10);
			glVertex2f(40, 40);
			glVertex2f(10, 40);
			glEnd();*/

			// render OpenGL here


			Display.sync(60);
			Display.update();
		}

		Display.destroy();
	}

	private static void rotateCamera(float a, float x, float y, float z, float d) {
		glTranslatef(0, 0, d);

		glRotatef(a, x, y, z);

		glTranslatef(0, 0, -d);

	}

	// Keep it as false as its more efficient and probably just as good tbh. Tho no point in removing alltogether
	public static boolean isBlockAt(float xPos, float yPos, float zPos){
		return false;
		/*for(ModelBlock box: enviroBoxList){
			if(box.xPos == xPos && box.yPos == yPos && box.zPos == zPos){
				return true;
			}
		}
		return false;*/
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

				matrix.rotate((float) Math.toRadians(box2.zRotation), new Vector3f(0, 0, 1));
				matrix.rotate((float) Math.toRadians(box2.yRotation), new Vector3f(0, 1, 0));
				matrix.rotate((float) Math.toRadians(box2.xRotation), new Vector3f(1, 0, 0));
			}
			xPosMoveTo = -(matrix.m00 * box.xPos + matrix.m10 * box.yPos + matrix.m20 * box.zPos + matrix.m30);
			yPosMoveTo = -(matrix.m01 * box.xPos + matrix.m11 * box.yPos + matrix.m21 * box.zPos + matrix.m31);
			zPosMoveTo = -(matrix.m02 * box.xPos + matrix.m12 * box.yPos + matrix.m22 * box.zPos + matrix.m32);


			/*ModelBox currentBox = box;
			glPushMatrix();
			ArrayList<ModelBox> boxList = new ArrayList<ModelBox>();
			boxList.add(currentBox);
			while(currentBox.parent != null){
				currentBox = currentBox.parent;
				boxList.add(currentBox);
			}
			Collections.reverse(boxList);
			System.out.println(boxList);
			for(ModelBox box2 : boxList){
				glTranslatef(box2.xPos, box2.yPos, box2.zPos);

				glRotatef(box2.yRotation, 0, 1, 0);
				glRotatef(box2.zRotation, 0, 0, 1);
				glRotatef(box2.xRotation, 1, 0, 0);
			}
			Matrix4f matrix = Assets.getMatrix(GL_MODELVIEW_MATRIX);
			xPosMoveTo = -(matrix.m00 * box.xPos + matrix.m10 * box.yPos + matrix.m20 * box.zPos);
			yPosMoveTo = -(matrix.m01 * box.xPos + matrix.m11 * box.yPos + matrix.m21 * box.zPos);
			zPosMoveTo = (matrix.m02 * box.xPos + matrix.m12 * box.yPos + matrix.m22 * box.zPos);
			glPopMatrix();*/
		}
		else{
			xPosMoveTo = -box.xPos;
			yPosMoveTo = -box.yPos;
			zPosMoveTo = -box.zPos;
		}
	}
}
